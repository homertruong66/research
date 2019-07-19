package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.SubsPackageConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.view_model.SubsPackageConfigUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.SubsPackageConfig;
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
public class SubsPackageConfigServiceImpl implements SubsPackageConfigService {

    private Logger logger = Logger.getLogger(SubsPackageConfigServiceImpl.class);

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
    @Secured({SystemRole.ROLE_ADMIN})
    public SubsPackageConfigDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsPackageConfig pdo = validationService.getSubsPackageConfig(id, false);

        // do biz action
        return beanMapper.map(pdo, SubsPackageConfigDto.class);
    }
    
    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsPackageConfigDto update(String id, SubsPackageConfigUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        SubsPackageConfig detachedDO = beanMapper.map(updateModel, SubsPackageConfig.class);

        // validate biz rules
        SubsPackageConfig existingDO = validationService.getSubsPackageConfig(id, true);
        if (detachedDO.getAffiliateCount() != null) {
            int affiliateCount = detachedDO.getAffiliateCount().intValue();
            if (affiliateCount < SubsPackageConfig.MIN_AFFILIATE_COUNT || SubsPackageConfig.MAX_AFFILIATE_COUNT < affiliateCount) {
                throw new BusinessException(BusinessException.SUBS_PACKAGE_CONFIG_WRONG_AFFILIATE_COUNT,
                    String.format(BusinessException.SUBS_PACKAGE_CONFIG_WRONG_AFFILIATE_COUNT_MESSAGE, affiliateCount));
            }
        }

        if (detachedDO.getChannelCount() != null) {
            int channelCount = detachedDO.getChannelCount().intValue();
            if (channelCount < SubsPackageConfig.MIN_CHANNEL_COUNT || SubsPackageConfig.MAX_CHANNEL_COUNT < channelCount) {
                throw new BusinessException(BusinessException.SUBS_PACKAGE_CONFIG_WRONG_CHANNEL_COUNT,
                    String.format(BusinessException.SUBS_PACKAGE_CONFIG_WRONG_CHANNEL_COUNT_MESSAGE, channelCount));
            }
        }

        // do authorization

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, SubsPackageConfigDto.class);
    }


    // Utilities

}
