package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SubsGetflyConfigDto;
import com.rms.rms.common.dto.SubsGetflyConfigTestDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.view_model.SubsGetflyConfigUpdateModel;
import com.rms.rms.integration.GetflyClient;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.PackageConfigApplied;
import com.rms.rms.service.model.SubsGetflyConfig;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class SubsGetflyConfigServiceImpl implements SubsGetflyConfigService {

    private Logger logger = Logger.getLogger(SubsGetflyConfigServiceImpl.class);

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
    private GetflyClient getflyClient;
    
    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL})
    public SubsGetflyConfigDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsGetflyConfig pdo = validationService.getSubsGetflyConfig(id, false);

        // check Subscriber package
        PackageConfigApplied pca = validationService.getPackageConfigApplied(id, false);
        if (!pca.getHasGetfly()) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_HAVE_GETFLY,
                String.format(BusinessException.SUBSCRIBER_NOT_HAVE_GETFLY_MESSAGE, id));
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
        else { // called by TaskProcessor to process data to Getfly when Order is pushed by Web Plugin
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), id);
        }

        // do biz action
        return beanMapper.map(pdo, SubsGetflyConfigDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsGetflyConfigTestDto test(String id) throws BusinessException {
        logger.info("test: " + id);

        // process view model

        // validate biz rules
        SubsGetflyConfig existingDO = validationService.getSubsGetflyConfig(id, false);
        if (existingDO.getBaseUrl() == null || existingDO.getApiKey() == null) {
            throw new BusinessException(BusinessException.SUBS_GETFLY_CONFIG_BASEURL_APIKEY_NOT_FOUND,
                    String.format(BusinessException.SUBS_GETFLY_CONFIG_BASEURL_APIKEY_NOT_FOUND_MESSAGE, id));
        }

        // do authorization
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), existingDO.getId());

        // do biz action
        SubsGetflyConfigTestDto testDto = new SubsGetflyConfigTestDto();
        testDto.setSuccess(getflyClient.test(existingDO.getBaseUrl(), existingDO.getApiKey()));
        return testDto;
    }
    
    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsGetflyConfigDto update(String id, SubsGetflyConfigUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        SubsGetflyConfig detachedDO = beanMapper.map(updateModel, SubsGetflyConfig.class);

        // validate biz rules
        SubsGetflyConfig existingDO = validationService.getSubsGetflyConfig(id, true);

        // check Subscriber package
        PackageConfigApplied pca = validationService.getPackageConfigApplied(id, false);
        if (!pca.getHasGetfly()) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_HAVE_GETFLY,
                    String.format(BusinessException.SUBSCRIBER_NOT_HAVE_GETFLY_MESSAGE, id));
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

        return beanMapper.map(existingDO, SubsGetflyConfigDto.class);
    }


    // Utilities

}
