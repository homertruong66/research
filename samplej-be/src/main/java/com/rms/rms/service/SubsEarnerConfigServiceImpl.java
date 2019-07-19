package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SubsEarnerConfigDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.view_model.SubsEarnerConfigUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.SubsEarnerConfig;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class SubsEarnerConfigServiceImpl implements SubsEarnerConfigService {

    private static final Logger logger = Logger.getLogger(SubsEarnerConfigServiceImpl.class);

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
    public SubsEarnerConfigDto get (String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsEarnerConfig pdo = validationService.getSubsEarnerConfig(id, false);

        // do authorization
          // SubsAdmin can only get the SubsEarnerConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), id);

        // do biz action
        return beanMapper.map(pdo, SubsEarnerConfigDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsEarnerConfigDto update (String id, SubsEarnerConfigUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        SubsEarnerConfig detachedDO = beanMapper.map(updateModel, SubsEarnerConfig.class);

        // validate biz rules
        SubsEarnerConfig existingDO = validationService.getSubsEarnerConfig(id, true);

        // do authorization
          // SubsAdmin can only update the SubsEarnerConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, SubsEarnerConfigDto.class);
    }

}
