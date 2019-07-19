package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.config.properties.ApplicationProperties;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.PaymentDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyStringUtil;
import com.rms.rms.common.view_model.PaymentCreateModel;
import com.rms.rms.common.view_model.PaymentSearchCriteria;
import com.rms.rms.common.view_model.PaymentUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class PaymentServiceImpl implements PaymentService, InitializingBean {

    private Logger logger = Logger.getLogger(PaymentServiceImpl.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;

    private String decimalPattern;

    @Override
    public void afterPropertiesSet() {
        decimalPattern = applicationProperties.getDecimalPattern();
    }

    @Override
    @Secured(SystemRole.ROLE_ACCOUNTANT)
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PaymentDto approve(String id) throws BusinessException {
        logger.info("approve: " + id);

        // validate biz rules
        Payment pdo = validationService.getPayment(id, true);
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubscriberId();
        Agent agent = validationService.getAgent(pdo.getAffiliateId(), subscriberId, true);

        // check Payment value <= Affilate's remaining earning
        if (Double.compare(pdo.getValue(), agent.getEarning()) > 0) {
            throw new BusinessException(BusinessException.PAYMENT_VALUE_MORE_THAN_AFFILIATE_EARNING,
                    String.format(BusinessException.PAYMENT_VALUE_MORE_THAN_AFFILIATE_EARNING_MESSAGE,
                            MyStringUtil.toDecimalString(pdo.getValue(), decimalPattern),
                            MyStringUtil.toDecimalString(agent.getEarning(), decimalPattern)));
        }

        // check Payment value >= SubsConfig lowestPayment
        SubsConfig subsConfig = validationService.getSubsConfig(subscriberId, false);
        if (Double.compare(pdo.getValue(), subsConfig.getLowestPayment()) < 0) {
            throw new BusinessException(BusinessException.PAYMENT_VALUE_LESS_THAN_LOWEST_PAYMENT,
                    String.format(BusinessException.PAYMENT_VALUE_LESS_THAN_LOWEST_PAYMENT_MESSAGE,
                            MyStringUtil.toDecimalString(pdo.getValue(), decimalPattern),
                            MyStringUtil.toDecimalString(subsConfig.getLowestPayment(), decimalPattern)));
        }

        // check Affiliate's balance >= SubsConfig accountKeepingFee
        double newBalance = agent.getEarning() - pdo.getValue();
        if (Double.compare(newBalance, subsConfig.getAccountKeepingFee()) < 0) {
            throw new BusinessException(BusinessException.PAYMENT_NEW_BALANCE_LESS_THAN_ACCOUNT_KEEPING_FEE,
                    String.format(BusinessException.PAYMENT_NEW_BALANCE_LESS_THAN_ACCOUNT_KEEPING_FEE_MESSAGE,
                            MyStringUtil.toDecimalString(newBalance, decimalPattern),
                            MyStringUtil.toDecimalString(subsConfig.getAccountKeepingFee(), decimalPattern)));
        }

        // do authorization
            // SubsAdmin can only approve the Payment of its Subscriber
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        changeStatusOnAction(Payment.ACTION_APPROVE, pdo);
        genericDao.update(pdo);

        // subtract balance of the Affiliate
        agent.setEarning(agent.getEarning() - pdo.getValue());
        genericDao.update(agent);

        return beanMapper.map(pdo, PaymentDto.class);
    }

    @Override
    @Secured({ SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_ACCOUNTANT })
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PaymentDto create (PaymentCreateModel createModel) throws BusinessException {
        logger.info("create: " + createModel);

        // process view model
        String errors = createModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        Payment newDO = beanMapper.map(createModel, Payment.class);

        // validate biz rules
        Affiliate affiliate;
        String affiliateId = newDO.getAffiliateId();
        Subscriber subscriber;
        String subscriberId = newDO.getSubscriberId();

        // do authorization
          // Affiliate can only create Payment for him and one of its Subscribers
          // SubsAdmin (ROLE_ACCOUNTANT) can only create Payment for an Affiliate and its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        // if User logged with ROLE_AFFILIATE -> get Affiliate from logged Affiliate, Subscriber from createModel
        if ( isAffiliate ) {
            if ( StringUtils.isBlank(subscriberId) ) {
                throw new InvalidViewModelException("'subscriber_id' can't be empty!");
            }

            affiliate = genericDao.read(Affiliate.class, loggedUserDto.getId(), false);
            subscriber = validationService.getSubscriber(subscriberId, false);
        }
        // if User logged with ROLE_ACCOUNTANT -> get Subscriber from logged SubsAdmin, Affiliate from createModel
        else {
            if ( StringUtils.isBlank(affiliateId) ) {
                throw new InvalidViewModelException("'affiliate_id' can't be empty! &&");
            }

            SubsAdmin subsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            subscriber = genericDao.read(Subscriber.class, subsAdmin.getSubscriberId(), false);
            affiliate = validationService.getAffiliate(affiliateId, false);
        }
        Agent existingAgent = authorService.checkAffiliateAndSubscriber(affiliate.getId(), subscriber.getId());
        newDO.setCreatedBy(loggedUserDto.getId());

        // check Payment value <= Affilate's remaining earning
        if (Double.compare(newDO.getValue(), existingAgent.getEarning()) > 0) {
            throw new BusinessException(BusinessException.PAYMENT_VALUE_MORE_THAN_AFFILIATE_EARNING,
                    String.format(BusinessException.PAYMENT_VALUE_MORE_THAN_AFFILIATE_EARNING_MESSAGE,
                            MyStringUtil.toDecimalString(newDO.getValue(), decimalPattern),
                            MyStringUtil.toDecimalString(existingAgent.getEarning(), decimalPattern)));
        }

        // check Payment value >= SubsConfig lowestPayment
        SubsConfig subsConfig = validationService.getSubsConfig(subscriber.getId(), false);
        if (Double.compare(newDO.getValue(), subsConfig.getLowestPayment()) < 0) {
            throw new BusinessException(BusinessException.PAYMENT_VALUE_LESS_THAN_LOWEST_PAYMENT,
                    String.format(BusinessException.PAYMENT_VALUE_LESS_THAN_LOWEST_PAYMENT_MESSAGE,
                            MyStringUtil.toDecimalString(newDO.getValue(), decimalPattern),
                            MyStringUtil.toDecimalString(subsConfig.getLowestPayment(), decimalPattern)));
        }

        // check Affiliate's balance >= SubsConfig accountKeepingFee
        double newBalance = existingAgent.getEarning() - newDO.getValue();
        if (Double.compare(newBalance, subsConfig.getAccountKeepingFee()) < 0) {
            throw new BusinessException(BusinessException.PAYMENT_NEW_BALANCE_LESS_THAN_ACCOUNT_KEEPING_FEE,
                    String.format(BusinessException.PAYMENT_NEW_BALANCE_LESS_THAN_ACCOUNT_KEEPING_FEE_MESSAGE,
                            MyStringUtil.toDecimalString(newBalance, decimalPattern),
                            MyStringUtil.toDecimalString(subsConfig.getAccountKeepingFee(), decimalPattern)));
        }

        // do biz action
        newDO.setStatus(Payment.STATUS_NEW);
        newDO.setAffiliate(affiliate);
        newDO.setAffiliateId(affiliate.getId());
        newDO.setSubscriber(subscriber);
        newDO.setSubscriberId(subscriber.getId());
        Payment pdo = genericDao.create(newDO);

        PaymentDto dto = beanMapper.map(pdo, PaymentDto.class);
        dto.setValue(newDO.getValue());

        return dto;
    }

    @Secured({SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_ACCOUNTANT})
    public byte[] export(Date from, Date to) throws BusinessException {
        logger.info("export: " + " - from: " + from + " -> to: " + to);

        // create excel work book
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Payments");

        // create headers
        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Cộng tác viên");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Thuê bao");
        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Tên miền");
        Cell cell4 = row.createCell(3);
        cell4.setCellValue("Số tiền");
        Cell cell5 = row.createCell(4);
        cell5.setCellValue("Trạng thái");
        Cell cell6 = row.createCell(5);
        cell6.setCellValue("Lý do");

        // add Payments page by page
        SearchCriteria<PaymentSearchCriteria> searchCriteria = new SearchCriteria<>();
        int pageSize = 1000;
        int startRow = 1;
        searchCriteria.setCriteria(new PaymentSearchCriteria());
        searchCriteria.setPageIndex(1);
        searchCriteria.setPageSize(pageSize);

        // set up search criteria
        CustomCriteria customCriteria = new CustomCriteria();
        customCriteria.setValue("createdAt>=", from);
        customCriteria.setValue("createdAt<=", to);

        // render first page
        searchCriteria.setCustomCriteria(customCriteria);
        SearchResult<PaymentDto> searchResult = this.search(searchCriteria);    // authorization done in this
        logger.info("Export Page 1: " + searchResult.getList().size() + " items");
        this.renderPaymentsToExcelSheet(searchResult.getList(), sheet, startRow);

        // render the rest
        int numberOfPages = searchResult.getNumOfPages();
        for (int pageIndex = 2; pageIndex <= numberOfPages; pageIndex++) {
            searchCriteria.setPageIndex(pageIndex);
            searchResult = this.search(searchCriteria);
            logger.info("Export Page " + pageIndex + ": " + searchResult.getList().size() + " items");
            startRow += pageSize;
            this.renderPaymentsToExcelSheet(searchResult.getList(), sheet, startRow);
        }

        // write excel work book to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        }
        catch (Exception ex) {
            throw new RuntimeException("Can not write data to output stream");
        }
        finally {
            try {
                workbook.close();
                bos.close();
            } catch (IOException e) {
            }
        }
        byte[] contents = bos.toByteArray();

        return contents;
    }

    @Override
    @Secured({ SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_ACCOUNTANT })
    public PaymentDto get (String id) throws BusinessException {
        logger.info("get :" + id);

        // validate biz rules
        Payment pdo = validationService.getPayment(id, false);

        // do authorization
          // Affiliate can only get its Payment
          // SubsAdmin (ROLE_ACCOUNTANT) can only get the Payment of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        boolean isAccountant = SystemRole.hasAccountantRole(loggedUserDto.getRoles());
        if (isAffiliate) {
            if (!loggedUserDto.getId().equals(pdo.getAffiliateId()) ) {
                throw new BusinessException(BusinessException.PAYMENT_DATA_AUTHORIZATION,
                        String.format(BusinessException.PAYMENT_DATA_AUTHORIZATION_MESSAGE, id));
            }
        }
        else if (isAccountant) {
            String subscriberId = pdo.getSubscriberId();
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        // do biz action
        PaymentDto dto = beanMapper.map(pdo, PaymentDto.class);
        dto.setValue(pdo.getValue());

        return dto;
    }

    @Override
    @Secured(SystemRole.ROLE_ACCOUNTANT)
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PaymentDto reject(String id, String reason) throws BusinessException {
        logger.info("reject: " + id + " with reason: " + reason);

        // validate biz rules
        Payment pdo = validationService.getPayment(id, true);

        // do authorization
          // SubsAdmin can only reject the Payment of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        changeStatusOnAction(Payment.ACTION_REJECT, pdo);
        pdo.setReason(reason);
        genericDao.update(pdo);

        PaymentDto dto = beanMapper.map(pdo, PaymentDto.class);
        dto.setValue(pdo.getValue());

        return dto;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_ACCOUNTANT})
    public SearchResult<PaymentDto> search(SearchCriteria<PaymentSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Payment> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Payment criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Payment.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Payment());
        }

        // process custom criteria
        SearchResult<Payment> searchResult;
        SearchResult<PaymentDto> dtoSearchResult;
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
        List<String> affiliateIds = new ArrayList<>();
        if (customCriteria.keySet().contains("affiliate_email")) {
            String affiliateEmail = customCriteria.getValue("affiliate_email");
            List<String> userIdsByEmail = specificDao.getUserIdsByEmail(affiliateEmail);
            if (userIdsByEmail.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            customCriteria.remove("affiliate_email");
            affiliateIds.addAll(userIdsByEmail);
        }

        // do authorization
        // Admin can search all Payments
        // SubsAdmin (ROLE_ACCOUNTANT) can only search Payments of its Subscriber
        // Affiliate can only search its Payments
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isAdmin = SystemRole.hasAdminRole(loggedUserDto.getRoles());
        boolean isAccountant = SystemRole.hasAccountantRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());

        List<String> authAffiliateIds = new ArrayList<>();
        if (!isAdmin) {
            if (isAccountant) {
                SubsAdmin subsAdmin = validationService.getSubsAdmin(loggedUserDto.getId(),false);
                authAffiliateIds = specificDao.getAffiliateIdsBySubscriberId(subsAdmin.getSubscriberId());
                customCriteria.setValue("subscriberId", subsAdmin.getSubscriberId());
            } else if (isAffiliate) {
                if (searchCriteria.getCriteria().getAffiliateId() != null) {
                    searchCriteria.getCriteria().setAffiliateId(null);
                }
                Affiliate affiliate = validationService.getAffiliate(loggedUserDto.getId(), false);
                authAffiliateIds.add(affiliate.getId());
            }

            if (authAffiliateIds.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createDtoSearchResult(searchResult);
                return dtoSearchResult;
            }

            if (affiliateIds.size() > 0) {  // search by email
                affiliateIds.retainAll(authAffiliateIds);
                if (affiliateIds.size() == 0) {
                    searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                    dtoSearchResult = createDtoSearchResult(searchResult);
                    return dtoSearchResult;
                }
            } else {
                affiliateIds.addAll(authAffiliateIds);
            }
        }
        if (affiliateIds.size() > 0) {
            customCriteria.setValue("affiliateId", affiliateIds);
        }

        // do biz action
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({ SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_ACCOUNTANT })
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PaymentDto update (String id, PaymentUpdateModel updateModel) throws BusinessException {
        logger.info("update:" + updateModel);

        // process view model
        String errors = updateModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        Payment detachedDO = beanMapper.map(updateModel, Payment.class);

        // validate biz rules
        Payment existingDO = validationService.getPayment(id, true);
        if ( Payment.STATUS_APPROVED.equals(existingDO.getStatus()) ) {
            throw new BusinessException(BusinessException.PAYMENT_INVALID_ACTION,
                String.format(BusinessException.PAYMENT_INVALID_ACTION_MESSAGE, id));
        }

        // do authorization
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if ( isAffiliate ) {
            // loggedAffiliate is owner of the Payment?
            if ( !loggedUserDto.getId().equals(existingDO.getCreatedBy()) ) {
                throw new BusinessException(BusinessException.PAYMENT_DATA_AUTHORIZATION,
                    String.format(BusinessException.PAYMENT_DATA_AUTHORIZATION_MESSAGE, id));
            }

            // update Subscriber?
            String subscriberId = detachedDO.getSubscriberId();
            if (subscriberId != null && !existingDO.getSubscriberId().equals(subscriberId)) {
                Subscriber subscriber = validationService.getSubscriber(subscriberId, false);
                authorService.checkAffiliateAndSubscriber(loggedUserDto.getId(), subscriberId);
                existingDO.setSubscriber(subscriber);
            }
        }
        else {  // for sure, it's loggedSubsAdmin
            // loggedSubsAdmin & the Payment same Subscriber?
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            if ( !loggedSubsAdmin.getSubscriberId().equals(existingDO.getSubscriberId()) ) {
                throw new BusinessException(BusinessException.PAYMENT_DATA_AUTHORIZATION,
                    String.format(BusinessException.PAYMENT_DATA_AUTHORIZATION_MESSAGE, id));
            }

            // update Affiliate?
            String affiliateId = detachedDO.getAffiliateId();
            if (affiliateId != null && !existingDO.getAffiliateId().equals(affiliateId)) {
                Affiliate affiliate = validationService.getAffiliate(affiliateId, false);
                authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(), affiliateId);
                existingDO.setAffiliate(affiliate);
            }
        }

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);  // get value if any
        existingDO.setUpdatedBy(loggedUserDto.getId());

        // do biz action
        genericDao.update(existingDO);

        PaymentDto dto = beanMapper.map(existingDO, PaymentDto.class);
        dto.setValue(existingDO.getValue());

        return dto;
    }

    // Utilities
    private SearchResult<PaymentDto> createDtoSearchResult(SearchResult<Payment> searchResult) {
        SearchResult<PaymentDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<PaymentDto> dtos = new ArrayList<>();
        for (Payment pdo : searchResult.getList()) {
            PaymentDto dto = beanMapper.map(pdo, PaymentDto.class);
            dto.setValue(pdo.getValue());
            dtos.add(dto);
        }
        result.setList(dtos);

        return result;
    }

    private void changeStatusOnAction(String action, Payment payment) throws BusinessException {
        String fromStatus = payment.getStatus();
        String toStatus = Payment.Action2StatusMap.get(action);
        switch (action) {
            case Payment.ACTION_APPROVE:
                if (!isStatusChangeAllowed(fromStatus, toStatus)) {
                    throw new BusinessException(BusinessException.PAYMENT_APPROVE_ERROR,
                            String.format(BusinessException.PAYMENT_APPROVE_ERROR_MESSAGE, payment.getStatus()));
                }
                break;

            case Payment.ACTION_REJECT:
                if (!isStatusChangeAllowed(fromStatus, toStatus)) {
                    throw new BusinessException(BusinessException.PAYMENT_REJECT_ERROR,
                            String.format(BusinessException.PAYMENT_REJECT_ERROR_MESSAGE, payment.getStatus()));
                }
                break;

            default:
                throw new BusinessException(BusinessException.PAYMENT_INVALID_ACTION,
                        String.format(BusinessException.PAYMENT_INVALID_ACTION_MESSAGE, action));
        }
        payment.setStatus(toStatus);
    }

    private boolean isStatusChangeAllowed(String fromStatus, String toStatus) {
        if (StringUtils.isBlank(fromStatus) || StringUtils.isBlank(toStatus)) {
            return false;
        }

        fromStatus = fromStatus.toUpperCase();
        toStatus = toStatus.toUpperCase();
        String allowedStatuses = Payment.StatusFlow.get(fromStatus);

        return allowedStatuses != null && allowedStatuses.contains(toStatus);
    }

    private void renderPaymentsToExcelSheet(List<PaymentDto> paymentDtos, XSSFSheet sheet, int startRow) {
        for (PaymentDto paymentDto : paymentDtos) {
            Row row = sheet.createRow(startRow++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(paymentDto.getAffiliate().getFirstName() + " " + paymentDto.getAffiliate().getLastName());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(paymentDto.getSubscriber().getCompanyName());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(paymentDto.getSubscriber().getDomainName());
            Cell cell4 = row.createCell(3);
            cell4.setCellValue(paymentDto.getValue());
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(paymentDto.getStatus());
            Cell cell6 = row.createCell(5);
            cell6.setCellValue(paymentDto.getReason());
        }
    }

}
