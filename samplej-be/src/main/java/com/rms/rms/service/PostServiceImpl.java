package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.PostDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.view_model.PostCreateModel;
import com.rms.rms.common.view_model.PostSearchCriteria;
import com.rms.rms.common.view_model.PostUpdateModel;
import com.rms.rms.integration.HtmlClient;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.Category;
import com.rms.rms.service.model.Post;
import com.rms.rms.service.model.SubsAdmin;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
public class PostServiceImpl implements PostService {

    private Logger logger = Logger.getLogger(PostServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private HtmlClient htmlClient;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;
    
    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PostDto create(PostCreateModel createModel) throws BusinessException {
        logger.info("create: " + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Post newDO = beanMapper.map(createModel, Post.class);  // map url

        // validate biz rules
        Category category = null;
        if (newDO.getCategoryId() != null) {
            category = validationService.getCategory(newDO.getCategoryId(), false);
        }
        // get HTML from url to extract more data
        Document document = htmlClient.get(newDO.getUrl());
        newDO.setDescription(extractPostDescription(document));
        newDO.setThumbnail(extractPostThumbnail(document));
        newDO.setTitle(document.title());

        // do authorization
            // SubsAdmin can only create a Post that belong to its Subscriber's Category
        UserDto loggedUserDto = authenService.getLoggedUser();
        if (category != null) {
            SubsAdmin subsAdmin = validationService.getSubsAdmin(category.getSubsAdminId(), false);
            String subscriberId = subsAdmin.getSubscriberId();
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
            newDO.setCategory(category);
        }

        // do biz action
        newDO.setSubsAdminId(loggedUserDto.getId());
        Post pdo = genericDao.create(newDO);

        return beanMapper.map(pdo, PostDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(String id) throws BusinessException {
        logger.info("delete: " + id);

        // validate biz rules
        Post pdo = validationService.getPost(id, true);

        // do authorization
          // SubsAdmin can only delete the Post of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin subsAdmin = genericDao.read(SubsAdmin.class, pdo.getSubsAdminId(), false);
        String subscriberId = subsAdmin.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        genericDao.delete(pdo);
    }
    
    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public SearchResult<PostDto> search(SearchCriteria<PostSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Post> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Post criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Post.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Post());
        }

        // do authorization
          // SubsAdmin can only search Posts of its Subscriber
          // Affiliate can only search Posts of the Subscriber he belongs to
        String subscriberId = null;
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            subscriberId = loggedSubsAdmin.getSubscriberId();
        }
        else if (isAffiliate) {
            if (vmSearchCriteria.getCriteria() != null) {
                subscriberId = vmSearchCriteria.getCriteria().getSubscriberId();
            }

            if (subscriberId == null) {
                throw new BusinessException(BusinessException.POST_SUBSCRIBER_ID_NOT_PROVIDED,
                                            BusinessException.POST_SUBSCRIBER_ID_NOT_PROVIDED_MESSAGE);
            }

            authorService.checkAffiliateAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        SearchResult<Post> searchResult;
        SearchResult<PostDto> dtoSearchResult;
        List<String> subsAdminIds = specificDao.getSubsAdminIdsBySubscriberId(subscriberId);
        if (subsAdminIds.size() == 0) {
            searchResult = genericDao.generateEmptySearchResult(searchCriteria);
            dtoSearchResult = createDtoSearchResult(searchResult);
            return dtoSearchResult;
        }
        else {
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("subsAdminId", subsAdminIds);
        }

        // do biz action
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PostDto update(String id, PostUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + ", " + updateModel.toString());

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Post detachedDO = beanMapper.map(updateModel, Post.class);

        // validate  biz rule
        Post existingDO = validationService.getPost(id, true);

        // do authorization
            // SubsAdmin can only update Post of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin postAuthSubsAdmin = validationService.getSubsAdmin(existingDO.getSubsAdminId(), false);
        String postAuthSubscriberId = postAuthSubsAdmin.getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), postAuthSubscriberId);

        // is categoryId updated ?
        if (updateModel.getCategoryId() != null) {
            Category category = validationService.getCategory(detachedDO.getCategoryId(), false);

            // SubsAdmin can only update Post to its Subscriber's category
            SubsAdmin categoryAuthSubsAdmin = validationService.getSubsAdmin(category.getSubsAdminId(), false);
            String categoryAuthSubscriberId = categoryAuthSubsAdmin.getSubscriberId();
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), categoryAuthSubscriberId);

            detachedDO.setCategory(category);
        }

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);
        PostDto dto = beanMapper.map(existingDO, PostDto.class);

        return dto;
    }
    
    // Utilities
    private SearchResult<PostDto> createDtoSearchResult(SearchResult<Post> searchResult) {
        SearchResult<PostDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<PostDto> dtos = new ArrayList<>();
        for (Post pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, PostDto.class));
        }
        result.setList(dtos);

        return result;
    }

    private String extractPostDescription (Document document) {
        Elements elements = document.select("meta");
        for (Element element: elements) {
            if (element.hasAttr("name")) {
                String name = element.attr("name");
                if (name.equals("description")) {
                    return element.attr("content");
                }
            }
        }

        return "";
    }

    private String extractPostThumbnail (Document document) {
        Elements elements = document.select("img");
        elements.addAll(document.select("link[href]"));

        if (elements != null && elements.size() == 0) {
            return "";
        }

        for (Element element: elements) {
            if (element.hasAttr("src")) {
                String src = element.attr("src");
                if ( (src.contains("thumbnail") || src.contains("logo") || src.contains("icon")) &&
                     (src.contains(".jpg") || src.contains(".png"))  ) {
                    return src;
                }
            }
            else {
                String href = element.attr("href");
                if ( (href.contains("thumbnail") || href.contains("logo") || href.contains("icon")) &&
                     (href.contains(".jpg") || href.contains(".png")) )  {
                    return href;
                }
            }
        }

        return elements.get(0).attr("src");
    }
}
