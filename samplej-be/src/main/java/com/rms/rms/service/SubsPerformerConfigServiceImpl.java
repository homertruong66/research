package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SubsPerformerConfigDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.view_model.SubsPerformerConfigUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.SubsPerformerConfig;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class SubsPerformerConfigServiceImpl implements SubsPerformerConfigService {

    private static final Logger logger = Logger.getLogger(SubsPerformerConfigServiceImpl.class);

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
    public SubsPerformerConfigDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsPerformerConfig pdo = validationService.getSubsPerformerConfig(id, false);

        // do authorization
        // SubsAdmin can only get the SubsPerformerConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), id);

        // do biz action
        return beanMapper.map(pdo, SubsPerformerConfigDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsPerformerConfigDto update(String id, SubsPerformerConfigUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        SubsPerformerConfig detachedDO = beanMapper.map(updateModel, SubsPerformerConfig.class);

        // validate biz rules
        SubsPerformerConfig existingDO = validationService.getSubsPerformerConfig(id, true);

        // do authorization
        // SubsAdmin can only update the SubsPerformerConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, SubsPerformerConfigDto.class);
    }
}
