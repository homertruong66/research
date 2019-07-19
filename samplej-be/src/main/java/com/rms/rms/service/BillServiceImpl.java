package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.BillDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.BillSearchCriteria;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.Bill;
import com.rms.rms.service.model.SubsAdmin;
import com.rms.rms.service.model.Subscriber;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class BillServiceImpl implements BillService {

    private Logger logger = Logger.getLogger(BillServiceImpl.class);

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

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BillDto confirm(String id) throws BusinessException {
        logger.info("confirm: " + id);

        // validate biz rules
        Bill pdo = validationService.getBill(id, true);

        // do biz action
        String fromStatus = pdo.getStatus();
        String toStatus = Bill.Action2StatusMap.get(Bill.ACTION_CONFIRM);
        if (!isStatusChangeAllowed(fromStatus, toStatus)) {
            throw new BusinessException(BusinessException.BILL_CONFIRM_ERROR,
                    String.format(BusinessException.BILL_CONFIRM_ERROR_MESSAGE, pdo.getStatus()));
        }
        pdo.setStatus(toStatus);
        genericDao.update(pdo);

        return beanMapper.map(pdo, BillDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void generate() {
        logger.info("generate");

        Date startDate = java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(1));
        Date endDate = java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(LocalDate.now().getMonth().maxLength()));
        List<String> subscriberIds = specificDao.getUnbilledSubscriberIds(startDate, endDate);

        for (String subscriberId : subscriberIds) {
            Bill bill = new Bill();
            bill.setDeadline(endDate);
            bill.setFee(1000.0);
            bill.setDescription("Bill for " + (endDate.getMonth() + 1));
            bill.setStatus(Bill.STATUS_NEW);
            Subscriber subscriber = genericDao.read(Subscriber.class, subscriberId, false);
            bill.setSubscriber(subscriber);
            genericDao.create(bill);
        }
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_ACCOUNTANT})
    public SearchResult<BillDto> search(SearchCriteria<BillSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Bill> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);   // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Bill criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Bill.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Bill());
        }

        // do authorization
          // Admin can search all Bills
          // SubsAdmin (ROLE_ACCOUNTANT) can only search Bills of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = searchCriteria.getCriteria().getSubscriberId();
        boolean isAccountant = SystemRole.hasAccountantRole(loggedUserDto.getRoles());
        if (isAccountant) {
            SubsAdmin loggedSubsAdmin;
            if (subscriberId != null) {
                loggedSubsAdmin = authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
            }
            else {
                loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            }
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("subscriberId", loggedSubsAdmin.getSubscriberId());
        }

        // do biz action
        SearchResult<Bill> searchResult;
        SearchResult<BillDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }


    // Utilities
    private SearchResult<BillDto> createDtoSearchResult(SearchResult<Bill> searchResult) {
        SearchResult<BillDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<BillDto> dtos = new ArrayList<>();
        for (Bill pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, BillDto.class));
        }
        result.setList(dtos);

        return result;
    }

    private boolean isStatusChangeAllowed(String fromStatus, String toStatus) {
        if (StringUtils.isBlank(fromStatus) || StringUtils.isBlank(toStatus)) {
            return false;
        }

        fromStatus = fromStatus.toUpperCase();
        toStatus = toStatus.toUpperCase();
        String allowedStatuses = Bill.StatusFlow.get(fromStatus);

        return allowedStatuses != null && allowedStatuses.contains(toStatus);
    }
}