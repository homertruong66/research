package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.NotificationDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.NotificationCreateModel;
import com.rms.rms.common.view_model.NotificationSearchCriteria;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.Notification;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class NotificationServiceImpl implements NotificationService {

    private static Logger logger = Logger.getLogger(NotificationServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_ACCOUNTANT, SystemRole.ROLE_CHANNEL})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public NotificationDto create (NotificationCreateModel createModel) throws BusinessException {
        logger.info("create : " + createModel);

        // process view model
        String errors = createModel.validate();
        if( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        Notification newDO = beanMapper.map(createModel, Notification.class);

        // validate biz rules
        newDO.setStatus(Notification.UN_READ);
        validationService.getUser(newDO.getToUserId(), false);

        // do biz action
        Notification pdo = genericDao.create(newDO);

        return beanMapper.map(pdo, NotificationDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_ACCOUNTANT, SystemRole.ROLE_CHANNEL})
    public List<NotificationDto> create (List<NotificationCreateModel> createModels) throws BusinessException {
        logger.info("create list: " + createModels);

        List<NotificationDto> dtos = new ArrayList<>();

        for( NotificationCreateModel createModel : createModels ) {
            try {
                dtos.add(this.create(createModel));
            } catch( BusinessException e ) {
                logger.error("creating notification failed : " + e.getMessage());
            }
        }
        return dtos;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete (String id) throws BusinessException {
        logger.info("delete: " + id);

        //validate biz rule
        Notification pdo = validationService.getNotification(id, true);

        // do authorization
        UserDto loggedUserDto = authenService.getLoggedUser();
        if (!loggedUserDto.getId().equals(pdo.getToUserId())) {
            throw new BusinessException(BusinessException.NOTIFICATION_NOT_BELONG_TO_USER,
                    String.format(BusinessException.NOTIFICATION_NOT_BELONG_TO_USER_MESSAGE, pdo.getId(),loggedUserDto.getId()));
        }

        //do biz action
        genericDao.delete(pdo);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public NotificationDto markAsRead (String id) throws BusinessException {
        logger.info("markAsRead: " + id);

        // validate biz rule
        Notification pdo = validationService.getNotification(id, true);
        if (!pdo.getStatus().equals(Notification.UN_READ)) {
            throw new BusinessException(BusinessException.NOTIFICATION_STATUS_CANT_BE_MARK_AS_READ,
                    String.format(BusinessException.NOTIFICATION_STATUS_CANT_BE_MARK_AS_READ_MESSAGE, pdo.getStatus()));
        }

        // do authorization
        UserDto loggedUserDto = authenService.getLoggedUser();
        if (!loggedUserDto.getId().equals(pdo.getToUserId())) {
            throw new BusinessException(BusinessException.NOTIFICATION_NOT_BELONG_TO_USER,
                    String.format(BusinessException.NOTIFICATION_NOT_BELONG_TO_USER_MESSAGE, pdo.getId(),loggedUserDto.getId()));
        }

        // do biz action
        pdo.setStatus(Notification.READ);
        genericDao.update(pdo);
        return beanMapper.map(pdo, NotificationDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_ACCOUNTANT})
    public SearchResult<NotificationDto> search (SearchCriteria<NotificationSearchCriteria> vmSearchCriteria) throws BusinessException {
        // setup search criteria
        SearchCriteria<Notification> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);   // map sort, page info
        if( vmSearchCriteria.getCriteria() != null ) {
            // map search info
            Notification criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Notification.class);
            searchCriteria.setCriteria(criteria);
        } else {
            // no search info found, use default
            searchCriteria.setCriteria(new Notification());
        }

        // do authorization
          // LoggedUser can only search its Notifications
        UserDto loggedUserDto = authenService.getLoggedUser();
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
        customCriteria.setValue("toUserId", loggedUserDto.getId());

        // do biz action
        SearchResult<Notification> searchResult;
        SearchResult<NotificationDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = this.createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }


    // Utilities
    private SearchResult<NotificationDto> createDtoSearchResult (SearchResult<Notification> searchResult) {
        SearchResult<NotificationDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);

        List<NotificationDto> dtos = new ArrayList<>();
        for( Notification pdo : searchResult.getList() ) {
            dtos.add(beanMapper.map(pdo, NotificationDto.class));
        }
        result.setList(dtos);

        return result;
    }
}
