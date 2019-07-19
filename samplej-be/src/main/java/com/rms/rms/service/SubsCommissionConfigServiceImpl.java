package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.*;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.util.MyNumberUtil;
import com.rms.rms.common.view_model.ConditionCommissionConfigUpdateModel;
import com.rms.rms.common.view_model.LayerCommissionConfigUpdateModel;
import com.rms.rms.common.view_model.SubsCommissionConfigSearchCriteria;
import com.rms.rms.common.view_model.SubsCommissionConfigUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class SubsCommissionConfigServiceImpl implements SubsCommissionConfigService {

    private Logger logger = Logger.getLogger(SubsCommissionConfigServiceImpl.class);

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
    public SubsCommissionConfigDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        SubsCommissionConfig pdo = validationService.getSubsCommissionConfig(id, false);

        // do authorization
          // SubsAdmin can only get SubsCommissionConfig of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        if (pdo.getType().equals(SubsCommissionConfig.TYPE_COAN)) {
            MyBeanUtil.triggerLazyLoad(pdo.getLayerCommissionConfigs());
            return beanMapper.map(pdo, SubsCommissionConfigDto.class);
        }
        else if (pdo.getType().equals(SubsCommissionConfig.TYPE_COPPR) ||
                 pdo.getType().equals(SubsCommissionConfig.TYPE_COOV) ||
                 pdo.getType().equals(SubsCommissionConfig.TYPE_COASV)) {
            MyBeanUtil.triggerLazyLoad(pdo.getConditionCommissionConfigs());
            return beanMapper.map(pdo, SubsCommissionConfigDto.class);
        }
        else if (pdo.getType().equals(SubsCommissionConfig.TYPE_COPQ)) {
            MyBeanUtil.triggerLazyLoad(pdo.getConditionCommissionConfigs());
            SubsCommissionConfigDto dto = beanMapper.map(pdo, SubsCommissionConfigDto.class);
            for (ConditionCommissionConfigDto cccDto : dto.getConditionCommissionConfigs()) {
                Product product = validationService.getProduct(cccDto.getProductId(), false);
                cccDto.setProduct(beanMapper.map(product, ProductDto.class));
            }
            return dto;
        }
        else if (pdo.getType().equals(SubsCommissionConfig.TYPE_COPG)) {
            SubsCommissionConfigDto dto = beanMapper.map(pdo, SubsCommissionConfigDto.class);
            Set<PriorityGroupDto> priorityGroupDtos = new HashSet<>();
            for (PriorityGroup priorityGroup : pdo.getPriorityGroups()) {
                PriorityGroupDto priorityGroupDto = beanMapper.map(priorityGroup, PriorityGroupDto.class);
                Set<AffiliateDto> affiliates = priorityGroup
                    .getAffiliates()
                    .stream()
                    .map(pga -> beanMapper.map(pga.getAffiliate(), AffiliateDto.class))
                    .collect(Collectors.toSet());
                priorityGroupDto.setAffiliates(affiliates);
                priorityGroupDtos.add(priorityGroupDto);
            }
            dto.setPriorityGroups(priorityGroupDtos);

            return dto;
        }
        else if (pdo.getType().equals(SubsCommissionConfig.TYPE_COPS)) {
            SubsCommissionConfigDto dto = beanMapper.map(pdo, SubsCommissionConfigDto.class);
            Set<ProductSetDto> productSetDtos = new HashSet<>();
            for (ProductSet productSet : pdo.getProductSets()) {
                ProductSetDto productSetDto = beanMapper.map(productSet, ProductSetDto.class);
                Set<ProductDto> products = productSet
                        .getProducts()
                        .stream()
                        .map(pga -> beanMapper.map(pga.getProduct(), ProductDto.class))
                        .collect(Collectors.toSet());
                productSetDto.setProducts(products);
                productSetDtos.add(productSetDto);
            }
            dto.setProductSets(productSetDtos);

            return dto;
        }

        return beanMapper.map(pdo, SubsCommissionConfigDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public SearchResult<SubsCommissionConfigDto> search(SearchCriteria<SubsCommissionConfigSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<SubsCommissionConfig> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            SubsCommissionConfig criteria = beanMapper.map(vmSearchCriteria.getCriteria(), SubsCommissionConfig.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new SubsCommissionConfig());
        }

        // do authorization
          // SubsAdmin can only search SubsCommissionConfigs of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
        customCriteria.setValue("subscriberId", loggedSubsAdmin.getSubscriberId());

        // do biz action
        SearchResult<SubsCommissionConfig> searchResult;
        SearchResult<SubsCommissionConfigDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SubsCommissionConfigDto update(String id, SubsCommissionConfigUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + updateModel.toString());

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Set<ConditionCommissionConfigUpdateModel> cccUpdateModels = updateModel.getConditionCommissionConfigs();
        updateModel.setConditionCommissionConfigs(null);
        Set<LayerCommissionConfigUpdateModel> lccUpdateModels = updateModel.getLayerCommissionConfigs();
        updateModel.setLayerCommissionConfigs(null);
        SubsCommissionConfig detachedDO = beanMapper.map(updateModel, SubsCommissionConfig.class);
        if (updateModel.getCommission() != null) {
            detachedDO.setCommission(MyNumberUtil.roundDouble(updateModel.getCommission(), 2));
        }

        // validate biz rules
        SubsCommissionConfig existingDO = validationService.getSubsCommissionConfig(id, true);

        // do authorization
          // SubsAdmin can only update SubsCommissionConfig on its own Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // update COAN?
        if (lccUpdateModels != null && lccUpdateModels.size() > 0) {
            // check Subscriber usage limit on Layers
            PackageConfigApplied pca = validationService.getPackageConfigApplied(subscriberId, false);
            int maxLayerCount = pca.getLayerCount().intValue();
            int layerCount = lccUpdateModels.size();
            if (layerCount > maxLayerCount) {
                throw new BusinessException(BusinessException.SUBSCRIBER_LAYER_COUNT_REACH_LIMIT,
                    String.format(BusinessException.SUBSCRIBER_LAYER_COUNT_REACH_LIMIT_MESSAGE, maxLayerCount));
            }

            // check Subscriber usage expiration
            Date today = MyDateUtil.convertToUTCDate(new Date());
            if (pca.getUsageExpiredAt().before(today)) {
                throw new BusinessException(BusinessException.SUBSCRIBER_USAGE_EXPIRED,
                        String.format(BusinessException.SUBSCRIBER_USAGE_EXPIRED_MESSAGE, pca.getUsageExpiredAt()));
            }

            SubsPackageStatus subsPackageStatus = validationService.getSubsPackageStatus(subscriberId, true);
            subsPackageStatus.setLayerCount(Integer.valueOf(layerCount + 1));
            genericDao.update(subsPackageStatus);

            // remove old LayerCommissionConfigs
            for (LayerCommissionConfig lcc : existingDO.getLayerCommissionConfigs()) {
                genericDao.delete(lcc);
            }
            existingDO.setLayerCommissionConfigs(null);

            // add new LayerCommissionConfigs
            Set<LayerCommissionConfig> newLayerCommissionConfigs = new HashSet<>();
            for (LayerCommissionConfigUpdateModel lccUpdateModel : lccUpdateModels) {
                LayerCommissionConfig lcc = beanMapper.map(lccUpdateModel, LayerCommissionConfig.class);
                lcc.setSubsCommissionConfigId(id);
                lcc.setCommission(MyNumberUtil.roundDouble(lcc.getCommission(), 2));
                genericDao.create(lcc);
                newLayerCommissionConfigs.add(lcc);
            }
            existingDO.setLayerCommissionConfigs(newLayerCommissionConfigs);
        }
        else {
            MyBeanUtil.triggerLazyLoad(existingDO.getLayerCommissionConfigs());
        }

        // update COPPR, COPQ, COOV, COASV?
        if (cccUpdateModels != null && cccUpdateModels.size() > 0) {
            // remove old ConditionCommissionConfigs
            for (ConditionCommissionConfig ccc : existingDO.getConditionCommissionConfigs()) {
                genericDao.delete(ccc);
            }
            existingDO.setConditionCommissionConfigs(null);

            // add new ConditionCommissionConfigs
            Set<ConditionCommissionConfig> newConditionCommissionConfigs = new HashSet<>();
            for (ConditionCommissionConfigUpdateModel cccUpdateModel : cccUpdateModels) {
                ConditionCommissionConfig ccc = beanMapper.map(cccUpdateModel, ConditionCommissionConfig.class);
                ccc.setSubsCommissionConfig(existingDO);
                ccc.setCommission(MyNumberUtil.roundDouble(ccc.getCommission(), 2));
                if (ccc.getStartDate() != null) {
                    ccc.setStartDate(MyDateUtil.convertToUTCDate(ccc.getStartDate()));
                }
                if (ccc.getEndDate() != null) {
                    ccc.setEndDate(MyDateUtil.convertToUTCDate(ccc.getEndDate()));
                }
                genericDao.create(ccc);
                newConditionCommissionConfigs.add(ccc);
            }
            existingDO.setConditionCommissionConfigs(newConditionCommissionConfigs);
        }
        else {
            MyBeanUtil.triggerLazyLoad(existingDO.getConditionCommissionConfigs());
        }

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, SubsCommissionConfigDto.class);
    }


    // Utilities
    private SearchResult<SubsCommissionConfigDto> createDtoSearchResult(SearchResult<SubsCommissionConfig> searchResult) {
        SearchResult<SubsCommissionConfigDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<SubsCommissionConfigDto> dtos = new ArrayList<>();
        for (SubsCommissionConfig pdo : searchResult.getList()) {
            if (pdo.getType().equals(SubsCommissionConfig.TYPE_COAN)) {
                MyBeanUtil.triggerLazyLoad(pdo.getLayerCommissionConfigs());
            }
            else if (pdo.getType().equals(SubsCommissionConfig.TYPE_COPPR) ||
                     pdo.getType().equals(SubsCommissionConfig.TYPE_COPQ) ||
                     pdo.getType().equals(SubsCommissionConfig.TYPE_COOV) ||
                     pdo.getType().equals(SubsCommissionConfig.TYPE_COASV)) {
               MyBeanUtil.triggerLazyLoad(pdo.getConditionCommissionConfigs());
            }
            else if (pdo.getType().equals(SubsCommissionConfig.TYPE_COPG)) {
                MyBeanUtil.triggerLazyLoad(pdo.getPriorityGroups());
            }
            else if (pdo.getType().equals(SubsCommissionConfig.TYPE_COPS)) {
                MyBeanUtil.triggerLazyLoad(pdo.getProductSets());
            }
            dtos.add(beanMapper.map(pdo, SubsCommissionConfigDto.class));
        }
        result.setList(dtos);

        return result;
    }

}
