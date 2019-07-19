package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.PackageConfigAppliedDto;
import com.rms.rms.common.dto.SubscriberDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.view_model.SubscriberExtendModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.PackageConfigApplied;
import com.rms.rms.service.model.Subscriber;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class PackageConfigAppliedServiceImpl implements PackageConfigAppliedService {

    private Logger logger = Logger.getLogger(PackageConfigAppliedServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private GenericDao genericDao;
    
    @Override
    @Secured({SystemRole.ROLE_CHANNEL, SystemRole.ROLE_SUBS_ADMIN})
    public PackageConfigAppliedDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        PackageConfigApplied pdo = validationService.getPackageConfigApplied(id, false);

        // do authorization
            // SubsAdmin and Channel can only get PackageConfigApplied of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), id);
        }
        else {
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), id);
        }

        // do biz action
        return beanMapper.map(pdo, PackageConfigAppliedDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubscriberDto extend(String subscriberId, SubscriberExtendModel extendModel) throws BusinessException {
        logger.info("extend: " + subscriberId + ", " + extendModel.toString());

        // process view model
        String errors = extendModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }


        // validate biz rule
        Subscriber subscriber = validationService.getSubscriber(subscriberId,false);
        PackageConfigApplied existingDO = subscriber.getPackageConfigApplied();


        // do biz action
        existingDO.setUsageExpiredAt(MyDateUtil.addDate(existingDO.getUsageExpiredAt(), extendModel.getDays()));
        genericDao.update(existingDO);

        return beanMapper.map(subscriber, SubscriberDto.class);
    }

}
