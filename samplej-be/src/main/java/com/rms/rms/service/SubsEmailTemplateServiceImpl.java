package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SubsEmailTemplateDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.view_model.SubsEmailTemplateUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.Channel;
import com.rms.rms.service.model.SubsAdmin;
import com.rms.rms.service.model.SubsEmailTemplate;
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
public class SubsEmailTemplateServiceImpl implements SubsEmailTemplateService {

    private Logger logger = Logger.getLogger(SubsEmailTemplateServiceImpl.class);

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
    private SubsEmailTemplateService subsEmailTemplateService;

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public SubsEmailTemplateDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsEmailTemplate pdo = validationService.getSubsEmailTemplate(id, false);

        // do authorization
          // SubsAdmin can only get the SubsEmailTemplate of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubsEmailConfigId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        return beanMapper.map(pdo, SubsEmailTemplateDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL})
    public SubsEmailTemplateDto getBySubscriberIdAndType(String type, String subscriberId) throws BusinessException {
        logger.info("getBySubscriberIdAndType  : " + subscriberId + ", type: " + type);

        // process view model
        if (!SubsEmailTemplate.checkType(type)) {
            String errors = ("invalid 'type': " + type);
            throw new InvalidViewModelException(errors);
        }

        // validate biz rules
        SubsEmailTemplate pdo = validationService.getSubsEmailTemplateBySubscriberIdAndType(subscriberId, type, false);

        // do authorization
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else {
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        // do biz actions
        return beanMapper.map(pdo, SubsEmailTemplateDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL})
    public SubsEmailTemplateDto getByType (String type) throws BusinessException {
        logger.info("getByType  : " + type);

        // process view model
        if (!SubsEmailTemplate.checkType(type)) {
            String errors = ("invalid 'type': " + type);
            throw new InvalidViewModelException(errors);
        }

        // validate biz rules
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId;
        boolean isSubAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubAdmin) {
            SubsAdmin loggedSubsAdmin = validationService.getSubsAdmin(loggedUserDto.getId(),false);
            subscriberId = loggedSubsAdmin.getSubscriberId();
        }
        else {
            Channel loggedChannel = validationService.getChannel(loggedUserDto.getId(),false);
            subscriberId = loggedChannel.getSubscriberId();
        }
        SubsEmailTemplate pdo = validationService.getSubsEmailTemplateBySubscriberIdAndType(subscriberId, type, false);

        // do authorization
        if (isSubAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        } else {
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        // do biz action
        return beanMapper.map(pdo, SubsEmailTemplateDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsEmailTemplateDto update(String id, SubsEmailTemplateUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        SubsEmailTemplate detachedDO = beanMapper.map(updateModel, SubsEmailTemplate.class);

        // validate biz rules
        SubsEmailTemplate existingDO = validationService.getSubsEmailTemplate(id, true);

        // do authorization
          // SubsAdmin can only update the SubsEmailTemplate of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getSubsEmailConfigId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, SubsEmailTemplateDto.class);
    }

}
