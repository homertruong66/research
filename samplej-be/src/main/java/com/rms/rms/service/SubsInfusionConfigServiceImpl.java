package com.rms.rms.service;

import com.rms.rms.common.config.properties.ApplicationProperties;
import com.rms.rms.common.config.properties.InfusionServerProperties;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SubsInfusionConfigDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.util.MyInfusionUtil;
import com.rms.rms.common.view_model.SubsInfusionConfigAuthorizeModel;
import com.rms.rms.integration.InfusionClient;
import com.rms.rms.integration.exception.InfusionIntegrationException;
import com.rms.rms.integration.model.InfusionData;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.PackageConfigApplied;
import com.rms.rms.service.model.SubsAdmin;
import com.rms.rms.service.model.SubsInfusionConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class SubsInfusionConfigServiceImpl implements SubsInfusionConfigService {

    private Logger logger = Logger.getLogger(SubsInfusionConfigServiceImpl.class);

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

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private InfusionServerProperties infusionServerProperties;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private InfusionClient infusionClient;
    
    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL})
    public SubsInfusionConfigDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsInfusionConfig pdo = validationService.getSubsInfusionConfig(id, false);

        // check Subscriber package
        PackageConfigApplied pca = validationService.getPackageConfigApplied(id, false);
        if (!pca.getHasInfusion()) {
            throw new BusinessException(BusinessException.SUBSCRIBER_NOT_HAVE_INFUSION,
                    String.format(BusinessException.SUBSCRIBER_NOT_HAVE_INFUSION_MESSAGE, id));
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
        else {  // called by TaskProcessor to process data to Infusion when Order is pushed by Web Plugin
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), id);
        }

        // do biz action
        return beanMapper.map(pdo, SubsInfusionConfigDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL, SystemRole.ROLE_AFFILIATE})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String getAccessToken(String id) throws BusinessException, IOException {
        logger.info("getAccessToken: " + id);

        SubsInfusionConfig existingDO = validationService.getSubsInfusionConfig(id, true);
        if (existingDO.getAccessToken() == null) {
            throw new BusinessException(BusinessException.SUBS_INFUSION_CONFIG_ACCESS_TOKEN_UNAUTHORIZED,
                    String.format(BusinessException.SUBS_INFUSION_CONFIG_ACCESS_TOKEN_UNAUTHORIZED_MESSAGE, id));
        }

        if (MyInfusionUtil.checkExpired(existingDO.getExpiredDate())) {
            InfusionData data;
            try {
                data = infusionClient.refreshAccessToken(infusionServerProperties.getClientId(),
                            infusionServerProperties.getClientSecret(), existingDO.getRefreshToken());

                mapInfusionData2SubsInfusionConfig(data, existingDO);
                genericDao.update(existingDO);
            }
            catch (InfusionIntegrationException iie) {
                logger.error("getAccessToken failed: " + iie.getMessage(), iie);
                throw new RuntimeException("can not refresh token: " + iie.getMessage(), iie);
            }

            return data.getAccessToken();
        }

        return existingDO.getAccessToken();
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public String getAuthorizeUrl(SubsInfusionConfigAuthorizeModel authorizeModel) throws BusinessException {
        logger.info("getAuthorizeUrl: " + authorizeModel.toString());

        // process view model
        authorizeModel.escapeHtml();
        String errors = authorizeModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        String feUrl = authorizeModel.getFeUrl();

        // store feUrl into redis
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin subsAdmin = validationService.getSubsAdmin(loggedUserDto.getId(), false);
        setUrl(String.format(URL_KEY_TEMPLATE, subsAdmin.getSubscriberId()), feUrl);

        // infusion url for authorization
        String redirectUri = applicationProperties.getApiPath() + "/v1/subs_infusion_configs/code/" + subsAdmin.getSubscriberId();
        StringBuilder sbAuthorizeUrl = new StringBuilder();
        sbAuthorizeUrl.append(infusionServerProperties.getAuthorizeUrl());
        sbAuthorizeUrl.append("?client_id=").append(infusionServerProperties.getClientId());
        sbAuthorizeUrl.append("&redirect_uri=").append(redirectUri);
        sbAuthorizeUrl.append("&response_type=code");
        sbAuthorizeUrl.append("&scope=full");

        return sbAuthorizeUrl.toString();
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String requestAccessToken(String id, String code) throws BusinessException, IOException {
        logger.info("requestAccessToken: " + id + " code: " + code);

        SubsInfusionConfig existingDO = validationService.getSubsInfusionConfig(id, true);
        String redirectUri = applicationProperties.getApiPath() + "/v1/subs_infusion_configs/code/" + existingDO.getId();
        try {
            InfusionData data = infusionClient.requestAccessToken(infusionServerProperties.getClientId(), infusionServerProperties.getClientSecret(), code, redirectUri);

            // map data to existingDO
            mapInfusionData2SubsInfusionConfig(data, existingDO);

            // do biz action
            genericDao.update(existingDO);
        }
        catch (InfusionIntegrationException iie) {
            logger.error("requestAccessToken failed: " + iie.getMessage(), iie);
            throw new RuntimeException("can not request access token: " + iie.getMessage(), iie);
        }

        // get feUrl from redis
        String feUrl = getAndDeleteUrlInCache(String.format(URL_KEY_TEMPLATE, existingDO.getId()));
        if (feUrl == null) {
            return "http://rms.com.vn";
        }

        return feUrl;
    }


    // Utilities
    private String getAndDeleteUrlInCache(String key) {
        if (key == null) {
            return null;
        }
        String valueFromRedis = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        if (StringUtils.isNotEmpty(valueFromRedis)) {
            return valueFromRedis;
        }
        return null;
    }

    private void mapInfusionData2SubsInfusionConfig(InfusionData data, SubsInfusionConfig subsInfusionConfig) {
        subsInfusionConfig.setAccessToken(data.getAccessToken());
        subsInfusionConfig.setRefreshToken(data.getRefreshToken());
        Date expiredDate = new Date(); // today
        expiredDate = MyDateUtil.addDate(expiredDate, data.getExpiresInSeconds() / 86400);
        subsInfusionConfig.setExpiredDate(expiredDate);
    }

    private void setUrl(String key, String feUrl) {
        if (key != null && feUrl != null) {
            redisTemplate.opsForValue().set(key, feUrl);
        }
    }
}
