package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.ProductDto;
import com.rms.rms.common.dto.ProductSetDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.util.MyNumberUtil;
import com.rms.rms.common.view_model.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class ProductSetServiceImpl implements ProductSetService {
    private static Logger logger = Logger.getLogger(ProductSetServiceImpl.class);

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
    @Secured({ SystemRole.ROLE_SUBS_ADMIN })
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProductSetDto addProducts (String id, ProductSetProductsAssignModel assignModel) throws BusinessException {
        logger.info("addProducts :" + id + ", assignModel : " + assignModel);

        // process view model
        String errors = assignModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        List<String> productIds = assignModel.getProductIds();

        // validate biz rules
        ProductSet pdo = validationService.getProductSet(id, false);
        String assignErrors = "";
        for (String productId : productIds) {
            if ( isProductInProductSet(productId, pdo.getProducts()) ) {
                assignErrors += String.format(BusinessException.PRODUCT_SET_PRODUCT_ALREADY_ASSIGNED_MESSAGE,
                                              productId, id) + " ! && ";
                continue;
            }

            Product product = genericDao.read(Product.class, productId, false);
            if ( product == null ) {
                assignErrors += String.format(BusinessException.PRODUCT_NOT_FOUND_MESSAGE, productId) + " ! && ";
                continue;
            }

            // check Product and Product Set belong to the same Subscriber
            SubsCommissionConfig scg = genericDao.read(SubsCommissionConfig.class, pdo.getSubsCommissionConfigId(), false);
            String subscriberId = scg.getSubscriberId();
            List<String> prodIds = specificDao.getProductIdsBySubscriberId(subscriberId);
            if (!prodIds.contains(productId)) {
                assignErrors += String.format(BusinessException.PRODUCT_SET_PRODUCT_NOT_SAME_SUBSCRIBER_MESSAGE, productId, pdo.getId()) + " ! && ";
                continue;
            }

            // do biz action
            ProductSetProduct newProductSetProduct = new ProductSetProduct();
            newProductSetProduct.setProductSet(pdo);
            newProductSetProduct.setProduct(product);

            genericDao.create(newProductSetProduct);
            pdo.getProducts().add(newProductSetProduct);
        }

        // do authorization
          // SubsAdmin can only add Products to the ProductSet whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubsCommissionConfig().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        ProductSetDto productSetDto = beanMapper.map(pdo, ProductSetDto.class);
        Set<ProductDto> products = pdo
            .getProducts()
            .stream()
            .map(productSetProduct -> beanMapper.map(productSetProduct.getProduct(), ProductDto.class))
            .collect(Collectors.toSet());

        productSetDto.setProducts(products);
        productSetDto.setErrors(assignErrors);

        return productSetDto;
    }

    @Override
    @Secured({ SystemRole.ROLE_SUBS_ADMIN })
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProductSetDto create (String subsCommissionConfigId, ProductSetCreateModel createModel) throws BusinessException {
        logger.info("create: " + subsCommissionConfigId + ", createModel " + createModel);

        // process view model
        String errors = createModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        ProductSet newDO = beanMapper.map(createModel, ProductSet.class);
        newDO.setCommission(MyNumberUtil.roundDouble(createModel.getCommission(), 2));
        newDO.setStartDate(MyDateUtil.convertToUTCDate(newDO.getStartDate()));
        newDO.setEndDate(MyDateUtil.convertToUTCDate(newDO.getEndDate()));

        // validate biz rules
        SubsCommissionConfig subsCommissionConfig = validationService.getSubsCommissionConfig(subsCommissionConfigId, false);
        if ( !subsCommissionConfig.getIsEnabled() ) {
            throw new BusinessException(BusinessException.SUBS_COMMISSION_CONFIG_IS_DISABLED,
                String.format(BusinessException.SUBS_COMMISSION_CONFIG_IS_DISABLED_MESSAGE, subsCommissionConfigId));
        }

        // do authorization
          // SubsAdmin can only create a ProductSet whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = subsCommissionConfig.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        newDO.setSubsCommissionConfig(subsCommissionConfig);
        ProductSet pdo = genericDao.create(newDO);

        return beanMapper.map(pdo, ProductSetDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public ProductSetDto get (String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        ProductSet pdo = validationService.getProductSet(id, false);

        // do authorization
          // SubsAdmin can only get the ProductSet whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubsCommissionConfig().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        ProductSetDto productSetDto = beanMapper.map(pdo, ProductSetDto.class);
        Set<ProductDto> products = pdo
            .getProducts()
            .stream()
            .map(productSetProduct -> beanMapper.map(productSetProduct.getProduct(), ProductDto.class))
            .collect(Collectors.toSet());
        productSetDto.setProducts(products);

        return productSetDto;
    }

    @Override
    @Secured({ SystemRole.ROLE_SUBS_ADMIN })
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProductSetDto removeProducts(String id, ProductSetProductsUnassignModel unassignModel) throws BusinessException {
        logger.info("removeProducts:" + id + ", unassignModel : " + unassignModel);

        // process view model
        String errors = unassignModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        List<String> productIds = unassignModel.getProductIds();

        // validate biz rules
        ProductSet pdo = validationService.getProductSet(id, false);

        // do authorization
          // SubsAdmin can only remove Products of the ProductSet whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getSubsCommissionConfig().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        String unassignErrors = "";
        for (String productId : productIds) {
            ProductSetProduct existingProductSetProduct = pdo
                .getProducts()
                .stream()
                .filter(productSetProduct -> productSetProduct.getProductId().equals(productId))
                .findFirst().orElse(null);

            if ( existingProductSetProduct == null ) {
                unassignErrors += String.format(BusinessException.PRODUCT_SET_PRODUCT_NOT_IN_MESSAGE,
                                                id, productId) + " ! && ";
                continue;
            }

            // do biz action
            genericDao.delete(existingProductSetProduct);
            pdo.getProducts().removeIf(
                productSetProduct -> productSetProduct.getProductId().equals(productId)
            );
        }

        ProductSetDto productSetDto = beanMapper.map(pdo, ProductSetDto.class);
        Set<ProductDto> products = pdo
            .getProducts()
            .stream()
            .map(productSetProduct -> beanMapper.map(productSetProduct.getProduct(), ProductDto.class))
            .collect(Collectors.toSet());

        productSetDto.setProducts(products);
        productSetDto.setErrors(unassignErrors);

        return productSetDto;
    }

    @Override
    @Secured({ SystemRole.ROLE_SUBS_ADMIN })
    public SearchResult<ProductSetDto> search (SearchCriteria<ProductSetSearchCriteria> searchCriteria) throws BusinessException {
        logger.info("search: " + searchCriteria);

        // setup search criteria
        SearchCriteria<ProductSet> productSetCriteria = new SearchCriteria<>();
        beanMapper.map(searchCriteria, productSetCriteria);

        if ( searchCriteria.getCriteria() != null ) {
            ProductSet productSet = beanMapper.map(searchCriteria.getCriteria(), ProductSet.class);
            productSetCriteria.setCriteria(productSet);
        }
        else {
            productSetCriteria.setCriteria(new ProductSet());
        }

        // do authorization
          // SubsAdmin can only search ProductSets whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
        List<String> subsCommissionConfigIds = specificDao.getSubsCommissionConfigIdsBySubscriberId(loggedSubsAdmin.getSubscriberId());
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
        customCriteria.setValue("subsCommissionConfigId", subsCommissionConfigIds);

        // do biz
        SearchResult<ProductSet> searchResult;
        SearchResult<ProductSetDto> dtoSearchResult;
        searchResult = genericDao.search(productSetCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({ SystemRole.ROLE_SUBS_ADMIN })
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProductSetDto update (String id, ProductSetUpdateModel updateModel) throws BusinessException {
        logger.info("update, id:" + id + ", updateModel: " + updateModel);

        // process view model
        String errors = updateModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }
        ProductSet detachedDO = beanMapper.map(updateModel, ProductSet.class);
        if (updateModel.getCommission() != null) {
            detachedDO.setCommission(MyNumberUtil.roundDouble(updateModel.getCommission(), 2));
        }

        // validate biz rules
        ProductSet existingDO = validationService.getProductSet(id, true);

        // check the updateModel's endDate with existing startDate if it presents
        if ( detachedDO.getEndDate() != null && existingDO.getStartDate() != null && existingDO.getStartDate().after(detachedDO.getEndDate()) ) {
            throw new BusinessException(
                BusinessException.PRODUCT_SET_START_DATE_VS_END_DATE,
                String.format(
                    BusinessException.PRODUCT_SET_START_DATE_VS_END_DATE_MESSAGE,
                    MyDateUtil.getYYMMDDString(existingDO.getStartDate()),
                    MyDateUtil.getYYMMDDString(detachedDO.getEndDate())
                )
            );
        }

        // check the updateModel's startDate with existing endDate if it presents
        if ( detachedDO.getStartDate() != null && existingDO.getEndDate() != null && detachedDO.getStartDate().after(existingDO.getEndDate()) ) {
            throw new BusinessException(
                BusinessException.PRODUCT_SET_START_DATE_VS_END_DATE,
                String.format(
                    BusinessException.PRODUCT_SET_START_DATE_VS_END_DATE_MESSAGE,
                    MyDateUtil.getYYMMDDString(detachedDO.getStartDate()),
                    MyDateUtil.getYYMMDDString(existingDO.getEndDate())
                )
            );
        }

        // do authorization
          // SubsAdmin can only update the ProductSet whose Subscriber is its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = existingDO.getSubsCommissionConfig().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, ProductSetDto.class);
    }


    // Utilities
    private SearchResult<ProductSetDto> createDtoSearchResult (SearchResult<ProductSet> searchResult) {
        SearchResult<ProductSetDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);

        List<ProductSetDto> dtos = new ArrayList<>();
        for (ProductSet pdo : searchResult.getList()) {
            ProductSetDto productSetDto = beanMapper.map(pdo, ProductSetDto.class);
            Set<ProductDto> products = pdo
                    .getProducts()
                    .stream()
                    .map(productSetProduct -> beanMapper.map(productSetProduct.getProduct(), ProductDto.class))
                    .collect(Collectors.toSet());
            productSetDto.setProducts(products);
            dtos.add(productSetDto);
        }
        result.setList(dtos);

        return result;
    }

    private boolean isProductInProductSet(String productId, Set<ProductSetProduct> psProducts) {
        if ( productId == null || psProducts == null || psProducts.size() == 0 ) {
            return false;
        }

        for (ProductSetProduct psProduct : psProducts) {
            if ( productId.equals(psProduct.getProductId()) ) {
                return true;
            }
        }

        return false;
    }
}
