package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SubsEmailConfigDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.view_model.SubsEmailConfigUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.SubsEmailConfig;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class SubsEmailConfigServiceImpl implements SubsEmailConfigService {

    private Logger logger = Logger.getLogger(SubsEmailConfigServiceImpl.class);

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

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public SubsEmailConfigDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsEmailConfig pdo = validationService.getSubsEmailConfig(id, false);

        // do authorization
          // SubsAdmin can only get the SubsEmailConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // trigger lazy load of SubsEmailTemplates
        MyBeanUtil.triggerLazyLoad(pdo.getSubsEmailTemplates());

        return beanMapper.map(pdo, SubsEmailConfigDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsEmailConfigDto update(String id, SubsEmailConfigUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + updateModel.toString());

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        SubsEmailConfig detachedDO = beanMapper.map(updateModel, SubsEmailConfig.class);

        // validate biz rules
        SubsEmailConfig existingDO = validationService.getSubsEmailConfig(id, true);

        // do authorization
          // SubsAdmin can only update the SubsEmailConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, SubsEmailConfigDto.class);
    }
    
}
