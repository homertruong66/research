package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.*;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyBeanUtil;
import com.rms.rms.common.util.MyNumberUtil;
import com.rms.rms.common.view_model.*;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.Channel;
import com.rms.rms.service.model.Commission;
import com.rms.rms.service.model.Product;
import com.rms.rms.service.model.SubsAdmin;
import com.rms.rms.task_processor.TaskProcessorImportProducts;
import io.jsonwebtoken.lang.Collections;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ExecutorService;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class ProductServiceImpl implements ProductService {

    private static Logger logger = Logger.getLogger(ProductServiceImpl.class);

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;

    @Autowired
    private ValidationService validationService;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    @Autowired
    private TaskProcessorImportProducts tpImportProducts;

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProductDto create(ProductCreateModel createModel) throws BusinessException {
        logger.info("create: " + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Product newDO = beanMapper.map(createModel, Product.class);
        newDO.setPrice(MyNumberUtil.roundDouble(createModel.getPrice(), 2));

        // validate biz rules
        Channel channel = validationService.getChannel(newDO.getChannelId(), false);
        newDO.setChannel(channel);
        Map<String, Object> productParams = new HashMap<>();
        productParams.put("code", newDO.getCode());
        productParams.put("channelId", newDO.getChannelId());
        Product existingDO = genericDao.readUnique(Product.class, productParams, false);
        if (existingDO != null) {
            throw new BusinessException(BusinessException.PRODUCT_CODE_EXISTS_IN_CHANNEL,
                    String.format(BusinessException.PRODUCT_CODE_EXISTS_IN_CHANNEL_MESSAGE, newDO.getCode(), newDO.getChannelId()));
        }

        // do authorization
            // SubsAdmin can only create product for its Channel
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), channel.getSubscriberId());

        // do biz action
        Product pdo = genericDao.create(newDO);

        return beanMapper.map(pdo, ProductDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public ProductDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        Product pdo = validationService.getProduct(id, false);

        // do authorization
            // SubsAdmin can only get the Product of its Channel
        UserDto loggedUserDto = authenService.getLoggedUser();
        Channel channel = validationService.getChannel(pdo.getChannelId(), false);
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), channel.getSubscriberId());

        return beanMapper.map(pdo, ProductDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_CHANNEL})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Set<ProductDto> importProducts(ProductImportModel importModel) throws BusinessException {
        logger.info("importProducts: " + importModel);

        // process view model
        importModel.escapeHtml();
        String errors = importModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Set<ProductCreateModel> createModels = importModel.getProducts();
        Set<ProductDto> dtos = new HashSet<>();
        for (ProductCreateModel createModel : createModels) {
            Product newDO = beanMapper.map(createModel, Product.class);
            newDO.setPrice(MyNumberUtil.roundDouble(createModel.getPrice(), 2));

            // validate biz rules
            Channel channel = validationService.getChannel(newDO.getChannelId(), false);
            newDO.setChannel(channel);
            Map<String, Object> productParams = new HashMap<>();
            productParams.put("code", newDO.getCode());
            productParams.put("channelId", newDO.getChannelId());
            Product existingDO = genericDao.readUnique(Product.class, productParams, false);
            if (existingDO != null) {
                throw new BusinessException(BusinessException.PRODUCT_CODE_EXISTS_IN_CHANNEL,
                        String.format(BusinessException.PRODUCT_CODE_EXISTS_IN_CHANNEL_MESSAGE, newDO.getCode(), newDO.getChannelId()));
            }

            // do authorization
                // Channel can only create products for Channels of its Subscriber
            UserDto loggedUserDto = authenService.getLoggedUser();
            authorService.checkChannelAndSubscriber(loggedUserDto.getId(), channel.getSubscriberId());

            // do biz action
            Product pdo = genericDao.create(newDO);
            dtos.add(beanMapper.map(pdo, ProductDto.class));
        }

        return dtos;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public SearchResult<ProductDto> search(SearchCriteria<ProductSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Product> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);   // map sort, page info
        if( vmSearchCriteria.getCriteria() != null ) {
            // map search info
            Product criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Product.class);
            searchCriteria.setCriteria(criteria);
        } else {
            // no search info found, use default
            searchCriteria.setCriteria(new Product());
        }

        // do authorization
        String channelId = searchCriteria.getCriteria().getChannelId();
        UserDto loggedUserDto = authenService.getLoggedUser();
        SubsAdmin loggedSubsAdmin = validationService.getSubsAdmin(loggedUserDto.getId(), false);


        // process criteria
        SearchResult<Product> searchResult;
        SearchResult<ProductDto> dtoSearchResult;

        List<String> channelIds = specificDao.getChannelIdsBySubscriberId(loggedSubsAdmin.getSubscriberId());

        if( Collections.isEmpty(channelIds) ) {
            searchResult = genericDao.generateEmptySearchResult(searchCriteria);
            dtoSearchResult = createDtoSearchResult(searchResult);
            return dtoSearchResult;
        }

        if( channelId != null ) {
            if( !channelIds.contains(channelId) ) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
        } else { // get all products belong to subsadmin's channels
            searchCriteria.getCustomCriteria().setValue("channelId", channelIds);
        }

        // do biz action
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProductDto update(String id, ProductUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Product detachedDO = beanMapper.map(updateModel, Product.class);
        detachedDO.setPrice(MyNumberUtil.roundDouble(updateModel.getPrice(), 2));

        // validate biz rules
        Product existingDO = validationService.getProduct(id, true);
        if (detachedDO.getCode() != null && !detachedDO.getCode().equals(existingDO.getCode())) {
            Map<String, Object> productParams = new HashMap<>();
            productParams.put("code", detachedDO.getCode());
            productParams.put("channelId", existingDO.getChannelId());
            Product pdo = genericDao.readUnique(Product.class, productParams, false);
            if (pdo != null) {
                throw new BusinessException(BusinessException.PRODUCT_CODE_EXISTS_IN_CHANNEL,
                        String.format(BusinessException.PRODUCT_CODE_EXISTS_IN_CHANNEL_MESSAGE, detachedDO.getCode(), existingDO.getChannelId()));
            }
        }

        // do authorization
            // SubsAdmin can only update the Product of its Channel
        UserDto loggedUserDto = authenService.getLoggedUser();
        Channel channel = validationService.getChannel(existingDO.getChannelId(), false);
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), channel.getSubscriberId());

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, ProductDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void upload(MultipartFile file) throws BusinessException {
        logger.info("upload with file: " + file.getName());

        // import Products in background mode
        UserDto loggedUserDto = authenService.getLoggedUser();
        Runnable task = () -> tpImportProducts.process(file, loggedUserDto);
        taskExecutorService.submit(task);
    }

    @Override
    public ProductViewDto view(String id, ProductViewModel viewModel) throws BusinessException {
        logger.info("view :" + id + " - " + viewModel);

        // process view model
        String errors = viewModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }

        // validate biz rules
        Product product = validationService.getProduct(id, false);

        // do authorization
          // SubsAdmin can only view the Product of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = product.getChannel().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        ProductDto productDto = beanMapper.map(product, ProductDto.class);
        ChannelDto channelDto = beanMapper.map(product.getChannel(), ChannelDto.class);
        List<Commission> commissions = specificDao.getCommissionsByProductId(product.getId(),
                                                    viewModel.getFromDate(), viewModel.getToDate());

        ProductViewDto productViewDto = new ProductViewDto();
        productViewDto.setChannel(channelDto);
        productViewDto.setCommissions(this.createCommissionDtos(commissions));
        productViewDto.setProduct(productDto);

        return productViewDto;
    }

    // Utilities
    private SearchResult<ProductDto> createDtoSearchResult(SearchResult<Product> searchResult) {
        SearchResult<ProductDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<ProductDto> dtos = new ArrayList<>();
        for( Product pdo : searchResult.getList() ) {
            dtos.add(beanMapper.map(pdo, ProductDto.class));
        }
        result.setList(dtos);

        return result;
    }

    private List<CommissionDto> createCommissionDtos(List<Commission> commissions) {
        List<CommissionDto> dtos = new ArrayList<>();
        for( Commission pdo : commissions ) {
            dtos.add(beanMapper.map(pdo, CommissionDto.class));
        }
        return dtos;
    }
}
