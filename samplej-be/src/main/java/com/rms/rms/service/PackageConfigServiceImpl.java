package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.PackageConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.view_model.PackageConfigSearchCriteria;
import com.rms.rms.common.view_model.PackageConfigUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.PackageConfig;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class PackageConfigServiceImpl implements PackageConfigService {

    private Logger logger = Logger.getLogger(PackageConfigServiceImpl.class);

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
    public PackageConfigDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        PackageConfig pdo = validationService.getPackageConfig(id, false);

        // do biz action
        return beanMapper.map(pdo, PackageConfigDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public SearchResult<PackageConfigDto> search(SearchCriteria<PackageConfigSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<PackageConfig> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);   // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            PackageConfig criteria = beanMapper.map(vmSearchCriteria.getCriteria(), PackageConfig.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new PackageConfig());
        }

        // do biz action
        SearchResult<PackageConfig> searchResult;
        SearchResult<PackageConfigDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PackageConfigDto update(String id, PackageConfigUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        PackageConfig detachedDO = beanMapper.map(updateModel, PackageConfig.class);

        // validate biz rules
        PackageConfig existingDO = validationService.getPackageConfig(id, true);
        if (detachedDO.getAffiliateCount() != null) {
            checkAffiliateCount(detachedDO.getAffiliateCount().intValue(), existingDO.getType());
        }

        if (detachedDO.getChannelCount() != null) {
            checkChannelCount(detachedDO.getChannelCount().intValue(), existingDO.getType());
        }

        if (detachedDO.getLayerCount() != null) {
            checkLayerCount(detachedDO.getLayerCount().intValue(), existingDO.getType());
        }

        String usagePeriod = detachedDO.getUsagePeriod();
        if (usagePeriod != null) {
            if (!PackageConfig.checkUsagePeriod(usagePeriod)) {
                throw new BusinessException(BusinessException.PACKAGE_CONFIG_INVALID_USAGE_PERIOD,
                    String.format(BusinessException.PACKAGE_CONFIG_INVALID_USAGE_PERIOD_MESSAGE, usagePeriod));
            }
        }

        // do authorization

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, PackageConfigDto.class);
    }


    // Utilities
    private SearchResult<PackageConfigDto> createDtoSearchResult(SearchResult<PackageConfig> searchResult) {
        SearchResult<PackageConfigDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<PackageConfigDto> dtos = new ArrayList<>();
        for (PackageConfig pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, PackageConfigDto.class));
        }
        result.setList(dtos);

        return result;
    }

    private void checkAffiliateCount (int affiliateCount, String packageType) throws BusinessException {
        int minAffiliateCount = PackageConfig.MIN_AFFILIATE_COUNT;
        BusinessException affiliateCountBusinessException = new BusinessException(
            BusinessException.PACKAGE_CONFIG_WRONG_AFFILIATE_COUNT_FOR_TYPE,
            String.format(BusinessException.PACKAGE_CONFIG_WRONG_AFFILIATE_COUNT_FOR_TYPE_MESSAGE,
                          affiliateCount, packageType)
        );

        switch (packageType) {
            case PackageConfig.TYPE_TRIAL:
                if (affiliateCount < minAffiliateCount || PackageConfig.TRIAL_MAX_AFFILIATE_COUNT < affiliateCount) {
                    throw affiliateCountBusinessException;
                }
                break;
            case PackageConfig.TYPE_BASIC:
                if (affiliateCount < minAffiliateCount || PackageConfig.BASIC_MAX_AFFILIATE_COUNT < affiliateCount) {
                    throw affiliateCountBusinessException;
                }
                break;
            case PackageConfig.TYPE_VIP:
                if (affiliateCount < minAffiliateCount || PackageConfig.VIP_MAX_AFFILIATE_COUNT < affiliateCount) {
                    throw affiliateCountBusinessException;
                }
                break;
            case PackageConfig.TYPE_UP:
                if (affiliateCount < minAffiliateCount || PackageConfig.UP_MAX_AFFILIATE_COUNT < affiliateCount) {
                    throw affiliateCountBusinessException;
                }
                break;
            default:
                throw new RuntimeException("Invalid package type: " + packageType);

        }
    }

    private void checkChannelCount (int channelCount, String packageType) throws BusinessException {
        int minChannelCount = PackageConfig.MIN_CHANNEL_COUNT;
        BusinessException channelCountBusinessException = new BusinessException(
            BusinessException.PACKAGE_CONFIG_WRONG_CHANNEL_COUNT_FOR_TYPE,
            String.format(BusinessException.PACKAGE_CONFIG_WRONG_CHANNEL_COUNT_FOR_TYPE_MESSAGE,
                          channelCount, packageType)
        );

        switch (packageType) {
            case PackageConfig.TYPE_TRIAL:
                if (channelCount < minChannelCount || PackageConfig.TRIAL_MAX_CHANNEL_COUNT < channelCount) {
                    throw channelCountBusinessException;
                }
                break;
            case PackageConfig.TYPE_BASIC:
                if (channelCount < minChannelCount || PackageConfig.BASIC_MAX_CHANNEL_COUNT < channelCount) {
                    throw channelCountBusinessException;
                }
                break;
            case PackageConfig.TYPE_VIP:
                if (channelCount < minChannelCount || PackageConfig.VIP_MAX_CHANNEL_COUNT < channelCount) {
                    throw channelCountBusinessException;
                }
                break;
            case PackageConfig.TYPE_UP:
                if (channelCount < minChannelCount || PackageConfig.UP_MAX_CHANNEL_COUNT < channelCount) {
                    throw channelCountBusinessException;
                }
                break;
            default:
                throw new RuntimeException("Invalid package type: " + packageType);

        }
    }

    private void checkLayerCount (int layerCount, String packageType) throws BusinessException {
        int minLayerCount = PackageConfig.MIN_LAYER_COUNT;
        BusinessException layerCountBusinessException = new BusinessException(
            BusinessException.PACKAGE_CONFIG_WRONG_LAYER_COUNT_FOR_TYPE,
            String.format(BusinessException.PACKAGE_CONFIG_WRONG_LAYER_COUNT_FOR_TYPE_MESSAGE,
                          layerCount, packageType)
        );

        switch (packageType) {
            case PackageConfig.TYPE_TRIAL:
                if (layerCount < minLayerCount || PackageConfig.TRIAL_MAX_LAYER_COUNT < layerCount) {
                    throw layerCountBusinessException;
                }
                break;
            case PackageConfig.TYPE_BASIC:
                if (layerCount < minLayerCount || PackageConfig.BASIC_MAX_LAYER_COUNT < layerCount) {
                    throw layerCountBusinessException;
                }
                break;
            case PackageConfig.TYPE_VIP:
                if (layerCount < minLayerCount || PackageConfig.VIP_MAX_LAYER_COUNT < layerCount) {
                    throw layerCountBusinessException;
                }
                break;
            case PackageConfig.TYPE_UP:
                if (layerCount < minLayerCount || PackageConfig.UP_MAX_LAYER_COUNT < layerCount) {
                    throw layerCountBusinessException;
                }
                break;
            default:
                throw new RuntimeException("Invalid package type: " + packageType);

        }
    }


}
