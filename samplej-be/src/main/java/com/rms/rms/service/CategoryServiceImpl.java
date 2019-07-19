package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.CategoryDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.view_model.CategoryCreateModel;
import com.rms.rms.common.view_model.CategorySearchCriteria;
import com.rms.rms.common.view_model.CategoryUpdateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.Category;
import com.rms.rms.service.model.SubsAdmin;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class CategoryServiceImpl implements CategoryService {

    private Logger logger = Logger.getLogger(CategoryServiceImpl.class);

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
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CategoryDto create(CategoryCreateModel createModel) throws BusinessException {
        logger.info("create: " + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Category newDO = beanMapper.map(createModel, Category.class);

        // validate biz rules
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin subsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
        List<String> subsAdminIds = specificDao.getSubsAdminIdsBySubscriberId(subsAdmin.getSubscriberId());
        if (specificDao.isCategoryNameExistentInSubscriber(newDO.getName(), subsAdminIds)) {
            throw new BusinessException(BusinessException.CATEGORY_NAME_EXISTS_IN_SUBSCRIBER,
                    String.format(BusinessException.CATEGORY_NAME_EXISTS_IN_SUBSCRIBER_MESSAGE, newDO.getName(), subsAdmin.getSubscriberId()));
        }

        // do biz action
        newDO.setSubsAdminId(subsAdmin.getId());
        Category pdo = genericDao.create(newDO);

        return beanMapper.map(pdo, CategoryDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(String id) throws BusinessException {
        logger.info("delete: " + id);

        // validate biz rules
        Category pdo = validationService.getCategory(id, true);

        // do authorization
            // SubsAdmin can only delete Category of its own Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin subsAdmin = genericDao.read(SubsAdmin.class, pdo.getSubsAdminId(), false);
        String subscriberId = subsAdmin.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        genericDao.delete(pdo);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public CategoryDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        Category pdo = validationService.getCategory(id, false);

        // do authorization
            // SubsAdmin can only get the Category of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin subsAdmin = genericDao.read(SubsAdmin.class, pdo.getSubsAdminId(), false);
        String subscriberId = subsAdmin.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        return beanMapper.map(pdo, CategoryDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public SearchResult<CategoryDto> search(SearchCriteria<CategorySearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Category> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);   // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Category criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Category.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Category());
        }

        // do authorization
            // Admin can search all Categories
            // SubsAdmin can only search Categories of its Subscriber
            // Affiliate can only search Categories of its Subscribers
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            String loggedSubscriberId = loggedSubsAdmin.getSubscriberId();
            List<String> subsAdminIds = specificDao.getSubsAdminIdsBySubscriberId(loggedSubscriberId);
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("subsAdminId", subsAdminIds);
        }
        else if (isAffiliate) {
            List<String> subscriberIds = specificDao.getSubscriberIdsByAffiliateId(loggedUserDto.getId());
            List<String> subsAdminIds = new ArrayList<>();
            for (String subscriberId: subscriberIds) {
                subsAdminIds.addAll(specificDao.getSubsAdminIdsBySubscriberId(subscriberId));
            }
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("subsAdminId", subsAdminIds);
        }

        // do biz action
        SearchResult<Category> searchResult;
        SearchResult<CategoryDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CategoryDto update(String id, CategoryUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Category detachedDO = beanMapper.map(updateModel, Category.class);

        // validate biz rules
        Category existingDO = validationService.getCategory(id, true);
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
        List<String> subsAdminIds = specificDao.getSubsAdminIdsBySubscriberId(loggedSubsAdmin.getSubscriberId());
        if (detachedDO.getName() != null && !detachedDO.getName().equals(existingDO.getName())) {
            if (specificDao.isCategoryNameExistentInSubscriber(detachedDO.getName(), subsAdminIds)) {
                throw new BusinessException(BusinessException.CATEGORY_NAME_EXISTS_IN_SUBSCRIBER,
                        String.format(BusinessException.CATEGORY_NAME_EXISTS_IN_SUBSCRIBER_MESSAGE, detachedDO.getName(), loggedSubsAdmin.getSubscriberId()));
            }
        }

        // do authorization
            // SubsAdmin can only update the Category of its Subscriber
        SubsAdmin subsAdmin = genericDao.read(SubsAdmin.class, existingDO.getSubsAdminId(), false);
        String subscriberId = subsAdmin.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, CategoryDto.class);
    }


    // Utilities
    private SearchResult<CategoryDto> createDtoSearchResult(SearchResult<Category> searchResult) {
        SearchResult<CategoryDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, CategoryDto.class));
        }
        result.setList(dtos);

        return result;
    }
}
