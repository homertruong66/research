package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SubsGetResponseConfigDto;
import com.rms.rms.common.dto.SubsGetResponseConfigTestDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.view_model.SubsGetResponseConfigUpdateModel;
import com.rms.rms.integration.GetResponseClient;
import com.rms.rms.integration.exception.GetResponseIntegrationException;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.Channel;
import com.rms.rms.service.model.PackageConfigApplied;
import com.rms.rms.service.model.SubsAdmin;
import com.rms.rms.service.model.SubsGetResponseConfig;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class SubsGetResponseConfigServiceImpl implements SubsGetResponseConfigService {

    private Logger logger = Logger.getLogger(SubsGetResponseConfigServiceImpl.class);

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
    private GetResponseClient getResponseClient;

//    @Override
//    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL})
//    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public void createCustomFields() throws BusinessException {
//        logger.info("createCustomFields: ");
//
//        // validate biz rules
//        UserDto loggedUserDto = authenService.getLoggedUser();
//        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
//        String subscriberId;
//        if (isSubsAdmin) {
//            SubsAdmin subsAdmin = validationService.getSubsAdmin(loggedUserDto.getId(), false);
//            subscriberId = subsAdmin.getSubscriberId();
//        }
//        else {
//            Channel channel = validationService.getChannel(loggedUserDto.getId(), false);
//            subscriberId = channel.getSubscriberId();
//        }
//        SubsGetResponseConfig pdo = validationService.getSubsGetResponseConfig(subscriberId, false);
//
//        // check Subscriber package
//        PackageConfigApplied pca = validationService.getPackageConfigApplied(subscriberId, false);
//        if (!pca.getHasGetResponse()) {
//            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_HAVE_GETRESPONSE,
//                    String.format(BusinessException.SUBSCRIBER_NOT_HAVE_GETRESPONSE_MESSAGE, subscriberId));
//        }
//
//        Date today = MyDateUtil.convertToUTCDate(new Date());
//        if (pca.getUsageExpiredAt().before(today)) {
//            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
//                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
//        }
//
//        // do authorization
//
//        // do biz action
//        try {
//            SubsGetResponseConfigDto dto = beanMapper.map(pdo, SubsGetResponseConfigDto.class);
//
//            // address
//            String addressFieldId = getResponseClient.createCustomFields(dto, "address");
//            if (addressFieldId != null) {
//                pdo.setAddressFieldId(addressFieldId);
//            }
//
//            // phone_number
//            String phoneFieldId = getResponseClient.createCustomFields(dto, "phone_number");
//            if (phoneFieldId != null) {
//                pdo.setPhoneFieldId(phoneFieldId);
//            }
//
//            // birthday
//            String birthdayFieldId = getResponseClient.createCustomFields(dto, "birthday");
//            if (birthdayFieldId != null) {
//                pdo.setBirthdayFieldId(birthdayFieldId);
//            }
//
//            // facebook_link
//            String facebookLinkFieldId = getResponseClient.createCustomFields(dto, "facebook_link");
//            if (facebookLinkFieldId != null) {
//                pdo.setFacebookLinkFieldId(facebookLinkFieldId);
//            }
//
//            // password
//            String passwordFieldId = getResponseClient.createCustomFields(dto, "password");
//            if (passwordFieldId != null) {
//                pdo.setPasswordFieldId(passwordFieldId);
//            }
//
//            // referrer_email
//            String referrerEmailFieldId = getResponseClient.createCustomFields(dto, "referrer_email");
//            if (referrerEmailFieldId != null) {
//                pdo.setReferrerEmailFieldId(referrerEmailFieldId);
//            }
//
//            genericDao.update(pdo);
//
//        }
//        catch (GetResponseIntegrationException grie) {
//            logger.error("createCustomFields failed: " + grie.getMessage(), grie);
//        }
//    }
    
    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL})
    public SubsGetResponseConfigDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsGetResponseConfig pdo = validationService.getSubsGetResponseConfig(id, false);

        // check Subscriber package
        PackageConfigApplied pca = validationService.getPackageConfigApplied(id, false);
        if (!pca.getHasGetResponse()) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_HAVE_GETRESPONSE,
                String.format(BusinessException.SUBSCRIBER_NOT_HAVE_GETRESPONSE_MESSAGE, id));
        }

        Date today = MyDateUtil.convertToUTCDate(new Date());
        if (pca.getUsageExpiredAt().before(today)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
        }

        // do authorization
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), id);
        }
        else { // called by TaskProcessor to process data to GetResponse when Order is pushed by Web Plugin
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), id);
        }

        // do biz action
        return beanMapper.map(pdo, SubsGetResponseConfigDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void getCustomFieldIdByName(String name) throws BusinessException {
        logger.info("getCustomFieldIdByName: " + name);

        // validate biz rules
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        String subscriberId;
        if (isSubsAdmin) {
            SubsAdmin subsAdmin = validationService.getSubsAdmin(loggedUserDto.getId(), false);
            subscriberId = subsAdmin.getSubscriberId();
        }
        else {
            Channel channel = validationService.getChannel(loggedUserDto.getId(), false);
            subscriberId = channel.getSubscriberId();
        }
        SubsGetResponseConfig pdo = validationService.getSubsGetResponseConfig(subscriberId, false);

        // check Subscriber package
        PackageConfigApplied pca = validationService.getPackageConfigApplied(subscriberId, false);
        if (!pca.getHasGetResponse()) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_HAVE_GETRESPONSE,
                    String.format(BusinessException.SUBSCRIBER_NOT_HAVE_GETRESPONSE_MESSAGE, subscriberId));
        }

        Date today = MyDateUtil.convertToUTCDate(new Date());
        if (pca.getUsageExpiredAt().before(today)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
        }

        // do authorization

        // do biz action
        try {
            SubsGetResponseConfigDto dto = beanMapper.map(pdo, SubsGetResponseConfigDto.class);

            String phoneFieldId = getResponseClient.getCustomFieldIdByName(name, dto);
            if (phoneFieldId != null) {
                pdo.setPhoneFieldId(phoneFieldId);
            }

            genericDao.update(pdo);

        }
        catch (GetResponseIntegrationException grie) {
            logger.error("getCustomFieldIdByName failed: " + grie.getMessage(), grie);
        }
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsGetResponseConfigTestDto test(String id) throws BusinessException {
        logger.info("test: " + id);

        // process view model

        // validate biz rules
        SubsGetResponseConfig existingDO = validationService.getSubsGetResponseConfig(id, false);
        if (existingDO.getCampaignDefaultId() == null || existingDO.getApiKey() == null) {
            throw new BusinessException(BusinessException.SUBS_GETRESPONSE_CONFIG_CAMPAIGN_DEFAULT_APIKEY_NOT_FOUND,
                    String.format(BusinessException.SUBS_GETRESPONSE_CONFIG_CAMPAIGN_DEFAULT_APIKEY_NOT_FOUND_MESSAGE, id));
        }

        // do authorization
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), existingDO.getId());

        // do biz action
        SubsGetResponseConfigTestDto testDto = new SubsGetResponseConfigTestDto();
        testDto.setSuccess(getResponseClient.test(existingDO.getApiKey(), existingDO.getCampaignDefaultId()));
        return testDto;
    }
    
    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsGetResponseConfigDto update(String id, SubsGetResponseConfigUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        SubsGetResponseConfig detachedDO = beanMapper.map(updateModel, SubsGetResponseConfig.class);

        // validate biz rules
        SubsGetResponseConfig existingDO = validationService.getSubsGetResponseConfig(id, true);

        // check Subscriber package
        PackageConfigApplied pca = validationService.getPackageConfigApplied(id, false);
        if (!pca.getHasGetResponse()) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_HAVE_GETRESPONSE,
                    String.format(BusinessException.SUBSCRIBER_NOT_HAVE_GETRESPONSE_MESSAGE, id));
        }

        Date today = MyDateUtil.convertToUTCDate(new Date());
        if (pca.getUsageExpiredAt().before(today)) {
            throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                    String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
        }

        // do authorization
          // SubsAdmin can only update the SubsGetflyConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, SubsGetResponseConfigDto.class);
    }

}
