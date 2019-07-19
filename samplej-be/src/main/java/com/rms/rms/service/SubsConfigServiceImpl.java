package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SubsConfigDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyNumberUtil;
import com.rms.rms.common.view_model.SubsConfigUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.Domain;
import com.rms.rms.service.model.SubsConfig;
import com.rms.rms.service.model.Subscriber;
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
public class SubsConfigServiceImpl implements SubsConfigService {

    private Logger logger = Logger.getLogger(SubsConfigServiceImpl.class);

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

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public SubsConfigDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsConfig pdo = validationService.getSubsConfig(id, false);
        Subscriber subscriber = validationService.getSubscriber(id, false);

        // do authorization
          // SubsAdmin can only get the SubsConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        SubsConfigDto dto = beanMapper.map(pdo, SubsConfigDto.class);
        beanMapper.map(subscriber, dto);

        return dto;
    }

    @Override
    @Secured({SystemRole.ROLE_AFFILIATE})
    public String getTermsAndConditions(String id) throws BusinessException {
        logger.info("getTermsAndConditions: " + id);

        // validate biz rules
        SubsConfig pdo = validationService.getSubsConfig(id, false);

        // do authorization
            // Affiliate can only get TermsAndConditions of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkAffiliateAndSubscriber(loggedUserDto.getId(), pdo.getId());

        return pdo.getTermsAndConditions() != null ? pdo.getTermsAndConditions() : "";
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsConfigDto update(String id, SubsConfigUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        SubsConfig detachedDO = beanMapper.map(updateModel, SubsConfig.class);
        if (updateModel.getAccountKeepingFee() != null) {
            detachedDO.setAccountKeepingFee(MyNumberUtil.roundDouble(updateModel.getAccountKeepingFee(), 2));
        }
        if (updateModel.getLowestPayment() != null) {
            detachedDO.setLowestPayment(MyNumberUtil.roundDouble(updateModel.getLowestPayment(), 2));
        }
        Subscriber subscriberDetachedDO = beanMapper.map(updateModel, Subscriber.class);

        // validate biz rules
        SubsConfig existingDO = validationService.getSubsConfig(id, true);
        if (detachedDO.getCoanType() != null) {
            if (!detachedDO.getCoanType().equals("LEVEL") && !detachedDO.getCoanType().equals("TOWER")) {
                throw new BusinessException(BusinessException.SUBS_CONFIG_COAN_TYPE_INVALID,
                        BusinessException.SUBS_CONFIG_COAN_TYPE_INVALID_MESSAGE);
            }
        }

        Subscriber subscriberExistingDO = validationService.getSubscriber(id, true);
        if (subscriberDetachedDO.getDomainName() != null && !subscriberExistingDO.getDomainName().equals(subscriberDetachedDO.getDomainName())) {
            if (specificDao.isPropertyValueExistent(Subscriber.class, "domainName", subscriberDetachedDO.getDomainName())) {
                throw new BusinessException(BusinessException.SUBSCRIBER_DOMAIN_NAME_EXISTS,
                        String.format(BusinessException.SUBSCRIBER_DOMAIN_NAME_EXISTS_MESSAGE, subscriberDetachedDO.getDomainName()));
            }
        }

        if (subscriberDetachedDO.getCompanyName() != null && !subscriberExistingDO.getCompanyName().equals(subscriberDetachedDO.getCompanyName())) {
            if (specificDao.isPropertyValueExistent(Subscriber.class, "companyName", subscriberDetachedDO.getCompanyName())) {
                throw new BusinessException(BusinessException.SUBSCRIBER_COMPANY_NAME_EXISTS,
                        String.format(BusinessException.SUBSCRIBER_COMPANY_NAME_EXISTS_MESSAGE, subscriberDetachedDO.getCompanyName()));
            }
        }

        if (subscriberDetachedDO.getProvinceId() != null && !subscriberExistingDO.getProvinceId().equals(subscriberDetachedDO.getProvinceId())) {
            Domain province = validationService.getDomain(subscriberDetachedDO.getProvinceId(), false);
            subscriberDetachedDO.setProvince(province);
        }

        // do authorization
          // SubsAdmin can only update the SubsConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);
        MyBeanUtil.mapIgnoreNullProps(subscriberExistingDO, subscriberDetachedDO);

        // do biz action
        genericDao.update(subscriberExistingDO);
        genericDao.update(existingDO);

        SubsConfigDto dto = beanMapper.map(existingDO, SubsConfigDto.class);
        beanMapper.map(subscriberExistingDO, dto);

        return dto;
    }

}
