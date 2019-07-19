package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SubsRewardConfigDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.view_model.SubsRewardConfigUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.SubsRewardConfig;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class SubsRewardConfigServiceImpl implements SubsRewardConfigService {

    private static final Logger logger = Logger.getLogger(SubsRewardConfigServiceImpl.class);

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private ValidationService validationService;

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public SubsRewardConfigDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsRewardConfig pdo = validationService.getSubsRewardConfig(id, false);

        // do authorization
            // SubsAdmin can only get the SubsRewardConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), id);

        // do biz action
        return beanMapper.map(pdo, SubsRewardConfigDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public SubsRewardConfigDto update(String id, SubsRewardConfigUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        SubsRewardConfig detachedDO = beanMapper.map(updateModel, SubsRewardConfig.class);

        // validate biz rules
        SubsRewardConfig existingDO = validationService.getSubsRewardConfig(id, true);

        // do authorization
            // SubsAdmin can only update the SubsRewardConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, SubsRewardConfigDto.class);
    }
}
