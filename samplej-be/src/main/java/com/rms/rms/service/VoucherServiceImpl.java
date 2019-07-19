package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.dto.VoucherDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.util.MyEmailUtil;
import com.rms.rms.common.util.MyNumberUtil;
import com.rms.rms.common.view_model.VoucherCreateModel;
import com.rms.rms.common.view_model.VoucherSearchCriteria;
import com.rms.rms.common.view_model.VoucherSendModel;
import com.rms.rms.common.view_model.VoucherUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class VoucherServiceImpl implements VoucherService {

    private Logger logger = Logger.getLogger(VoucherServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public VoucherDto create(VoucherCreateModel createModel) throws BusinessException {
        logger.info("create: " + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Voucher newDO = beanMapper.map(createModel, Voucher.class);
        newDO.setBenefit(MyNumberUtil.roundDouble(newDO.getBenefit(), 2));
        newDO.setStartDate(MyDateUtil.convertToUTCDate(newDO.getStartDate()));
        newDO.setEndDate(MyDateUtil.convertToUTCDate(newDO.getEndDate()));

        // validate biz rules
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);

        // check Subscriber package
        String subscriberId = loggedSubsAdmin.getSubscriberId();
        PackageConfigApplied pca = validationService.getPackageConfigApplied(subscriberId, false);
        SubsPackageConfig spc = validationService.getSubsPackageConfig(subscriberId, false);
        if (!pca.getHasVoucher() && !spc.getHasVoucher()) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_HAVE_VOUCHER,
                    String.format(BusinessException.SUBSCRIBER_NOT_HAVE_VOUCHER_MESSAGE, subscriberId));
        }

        Date today = MyDateUtil.convertToUTCDate(new Date());
        if (pca.getUsageExpiredAt().before(today)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
        }

        if ( !Voucher.TYPE_TIME.equals(newDO.getType()) &&
             !Voucher.TYPE_VALUE.equals(newDO.getType())) {
            throw new BusinessException(BusinessException.VOUCHER_TYPE_INVALID,
                    String.format(BusinessException.VOUCHER_TYPE_INVALID_MESSAGE, newDO.getType()));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("code", newDO.getCode());
        params.put("subscriberId", subscriberId);
        Voucher existingDO = genericDao.readUnique(Voucher.class, params, false);
        if (existingDO != null) {
            throw new BusinessException(BusinessException.VOUCHER_CODE_EXISTS_IN_SUBSCRIBER,
                    String.format(BusinessException.VOUCHER_CODE_EXISTS_IN_SUBSCRIBER_MESSAGE, newDO.getCode(), subscriberId));
        }

        // do authorization

        // do biz action
        newDO.setSubscriber(loggedSubsAdmin.getSubscriber());
        newDO.setStatus(Voucher.STATUS_NEW);
        Voucher pdo = genericDao.create(newDO);

        return beanMapper.map(pdo, VoucherDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(String id) throws BusinessException {
        logger.info("delete: " + id);

        // validate biz rules
        Voucher pdo = validationService.getVoucher(id, true);

        // do authorization
            // SubsAdmin can only delete Voucher of its own Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), pdo.getSubscriberId());

        genericDao.delete(pdo);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public VoucherDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        Voucher pdo = validationService.getVoucher(id, false);

        // do authorization
            // SubsAdmin can only get the Voucher of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), pdo.getSubscriberId());

        return beanMapper.map(pdo, VoucherDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_SUBS_ADMIN})
    public SearchResult<VoucherDto> search(SearchCriteria<VoucherSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Voucher> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);   // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Voucher criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Voucher.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Voucher());
        }

        // do authorization
            // SubsAdmin can only search Vouchers of its Subscriber
            // Affiliate can only search Vouchers of it
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("subscriberId", loggedSubsAdmin.getSubscriberId());
        }
        else {
            List<String> ids = specificDao.getVoucherIdsByAffiliateId(loggedUserDto.getId());
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("id", ids);
        }

        // do biz action
        SearchResult<Voucher> searchResult;
        SearchResult<VoucherDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_AFFILIATE})
    public void send(VoucherSendModel sendModel) throws BusinessException, IOException {
        logger.info("process: " + sendModel);

        // process view model
        sendModel.escapeHtml();
        String errors = sendModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Set<String> ids = sendModel.getIds();
        Set<String> customerIds = sendModel.getCustomerIds();
        Set<String> providedEmails = sendModel.getEmails();

        // validate biz rules

        // do authorization
            // Affiliate can only process its Vouchers to its Customers
        UserDto loggedUserDto = authenService.getLoggedUser();
        if (!specificDao.isVoucherIdsBelongToAffiliateId(ids, loggedUserDto.getId())) {
            throw new BusinessException(BusinessException.VOUCHER_NOT_BELONG_TO_AFFILIATE,
                    String.format(BusinessException.VOUCHER_NOT_BELONG_TO_AFFILIATE_MESSAGE, ids, loggedUserDto.getId()));
        }

        if (!CollectionUtils.isEmpty(customerIds)) {
            if (!specificDao.isCustomerIdsBelongToAffiliateId(customerIds, loggedUserDto.getId())) {
                throw new BusinessException(BusinessException.CUSTOMER_NOT_BELONG_TO_AFFILIATE,
                        String.format(BusinessException.CUSTOMER_NOT_BELONG_TO_AFFILIATE_MESSAGE, customerIds, loggedUserDto.getId()));
            }
        }

        // do biz action
        List<Voucher> vouchers = specificDao.getVouchersByAffiliateId(ids, loggedUserDto.getId());

        List<String> customerEmails = new ArrayList<>();
        if (!CollectionUtils.isEmpty(customerIds)) {
            List<Customer> customers = specificDao.getCustomersByAffiliateId(customerIds, loggedUserDto.getId());
            customerEmails = customers
                    .stream()
                    .map(Customer::getEmail)
                    .collect(Collectors.toList());
        }

        if (!CollectionUtils.isEmpty(providedEmails)) {
            customerEmails.addAll(providedEmails);
        }

        for (Voucher voucher : vouchers) {
            String title = emailService.getSendVoucherEmailTitle();
            String content = emailService.getSendVoucherEmailContent();
            content = content.replace("[name]", voucher.getName());
            content = content.replace("[code]", voucher.getCode());
            content = content.replace("[benefit]", voucher.getBenefit().toString());
            content = content.replace("[startDate]", MyDateUtil.getYYMMDDString(voucher.getStartDate()));
            content = content.replace("[companyName]", voucher.getSubscriber().getCompanyName());

            String emailTemplate = emailService.getSystemEmailTemplate();
            emailTemplate = emailTemplate.replace("[title]", title);
            emailTemplate = emailTemplate.replace("[content]", content);

            List<Email> emails = new ArrayList<>();
            for (String customerEmail : customerEmails) {
                String customerName = customerEmail.split("@")[0];
                Email email = MyEmailUtil.createEmailFromEmailTemplate(title, emailTemplate, customerEmail, customerName);
                emails.add(email);
            }
            emailService.send(emails);
        }
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public VoucherDto update(String id, VoucherUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Voucher detachedDO = beanMapper.map(updateModel, Voucher.class);
        detachedDO.setBenefit(MyNumberUtil.roundDouble(detachedDO.getBenefit(), 2));
        detachedDO.setStartDate(MyDateUtil.convertToUTCDate(detachedDO.getStartDate()));
        detachedDO.setEndDate(MyDateUtil.convertToUTCDate(detachedDO.getEndDate()));

        // validate biz rules
        Voucher existingDO = validationService.getVoucher(id, true);
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);

        // check Subscriber package
        String subscriberId = loggedSubsAdmin.getSubscriberId();
        PackageConfigApplied pca = validationService.getPackageConfigApplied(subscriberId, false);
        SubsPackageConfig spc = validationService.getSubsPackageConfig(subscriberId, false);
        if (!pca.getHasVoucher() && !spc.getHasVoucher()) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_HAVE_VOUCHER,
                    String.format(BusinessException.SUBSCRIBER_NOT_HAVE_VOUCHER_MESSAGE, subscriberId));
        }

        Date today = MyDateUtil.convertToUTCDate(new Date());
        if (pca.getUsageExpiredAt().before(today)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
        }

        if ( detachedDO.getType() != null &&
             !Voucher.TYPE_TIME.equals(detachedDO.getType()) &&
             !Voucher.TYPE_VALUE.equals(detachedDO.getType())) {
            throw new BusinessException(BusinessException.VOUCHER_TYPE_INVALID,
                    String.format(BusinessException.VOUCHER_TYPE_INVALID_MESSAGE, detachedDO.getType()));
        }

        if (detachedDO.getCode() != null && !detachedDO.getCode().equals(existingDO.getCode())) {
            Map<String, Object> params = new HashMap<>();
            params.put("code", detachedDO.getCode());
            params.put("subscriberId", loggedSubsAdmin.getSubscriberId());
            Voucher pdo = genericDao.readUnique(Voucher.class, params, false);
            if (pdo != null) {
                throw new BusinessException(BusinessException.VOUCHER_CODE_EXISTS_IN_SUBSCRIBER,
                        String.format(BusinessException.VOUCHER_CODE_EXISTS_IN_SUBSCRIBER_MESSAGE, detachedDO.getCode(), loggedSubsAdmin.getSubscriberId()));
            }
        }

        // update both startDate & endDate are validated in view model
        // startDate & not endDate?
        if (detachedDO.getStartDate() != null && detachedDO.getEndDate() == null) {
            if (existingDO.getEndDate() != null) {  // compare startDate with existing endDate
                if (!detachedDO.getStartDate().before(existingDO.getEndDate())) {
                    throw new BusinessException(BusinessException.VOUCHER_START_DATE_UPDATE_INVALID,
                            BusinessException.VOUCHER_START_DATE_UPDATE_INVALID_MESSAGE);
                }
            }
        }
        // endDate & not startDate?
        if (detachedDO.getEndDate() != null && detachedDO.getStartDate() == null) { // update endDate, not startDate
            if (!existingDO.getStartDate().before(detachedDO.getEndDate())) { // compare existing startDate with endDate
                throw new BusinessException(BusinessException.VOUCHER_END_DATE_UPDATE_INVALID,
                        BusinessException.VOUCHER_END_DATE_UPDATE_INVALID_MESSAGE);
            }
        }

        // do authorization
            // SubsAdmin can only update the Voucher of its Subscriber
        authorService.checkSubsAdminAndSubscriber(loggedSubsAdmin.getId(), existingDO.getSubscriberId());

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, VoucherDto.class);
    }


    // Utilities
    private SearchResult<VoucherDto> createDtoSearchResult(SearchResult<Voucher> searchResult) {
        SearchResult<VoucherDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<VoucherDto> dtos = new ArrayList<>();
        for (Voucher pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, VoucherDto.class));
        }
        result.setList(dtos);

        return result;
    }
}
