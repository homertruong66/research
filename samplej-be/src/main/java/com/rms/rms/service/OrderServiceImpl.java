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
import com.rms.rms.common.util.MyJsonUtil;
import com.rms.rms.common.util.MyNumberUtil;
import com.rms.rms.common.view_model.*;
import com.rms.rms.integration.GetResponseClient;
import com.rms.rms.integration.GetflyClient;
import com.rms.rms.integration.InfusionClient;
import com.rms.rms.integration.exception.GetResponseIntegrationException;
import com.rms.rms.integration.exception.GetflyIntegrationException;
import com.rms.rms.integration.exception.InfusionIntegrationException;
import com.rms.rms.integration.model.GetResponseContact;
import com.rms.rms.integration.model.GetflyAccount;
import com.rms.rms.integration.model.InfusionContact;
import com.rms.rms.integration.model.InfusionTag;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class OrderServiceImpl implements OrderService {

    private Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private SubsInfusionConfigService subsInfusionConfigService;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;

    @Autowired
    private GetflyClient getflyClient;

    @Autowired
    private GetResponseClient getResponseClient;

    @Autowired
    private InfusionClient infusionClient;

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderDto approve(String id) throws BusinessException {
        logger.info("approve: " + id);

        // validate biz rules
        Order pdo = validationService.getOrder(id, true);

        // do authorization
            // SubsAdmin can only approve the Order of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getChannel().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        changeStatusOnAction(Order.ACTION_APPROVE, pdo);
        genericDao.update(pdo);

        return beanMapper.map(pdo, OrderDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderDto confirmCommissionsGeneratedDone(String id) throws BusinessException {
        logger.info("confirmCommissionsGeneratedDone: " + id);

        // validate biz rules
        Order pdo = validationService.getOrder(id, true);

        // do authorization
        // SubsAdmin can only mark the Order of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getChannel().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        changeStatusOnAction(Order.ACTION_CONFIRM_COMMISSIONS_DONE, pdo);
        genericDao.update(pdo);

        return beanMapper.map(pdo, OrderDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public Long count() throws BusinessException {
        logger.info("count: ");

        // do authorization
          // Admin can count all Orders
          // SubsAdmin can only count Orders of its Subscriber
          // Affiliate can only count its Orders
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            return specificDao.countOrdersBySubscriberId(loggedSubsAdmin.getSubscriberId());
        }
        else if (isAffiliate) {
            return specificDao.countOrdersByAffiliateId(loggedUserDto.getId());
        }

        return Long.valueOf(genericDao.count(Order.class));
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Long countByAffiliateId(String affiliateId) throws BusinessException {
        logger.info("countByAffiliateId: " + affiliateId);

        // validate biz rules
        validationService.getAffiliate(affiliateId, false);

        // do authorization
          // Admin can count all Orders of the Affiliate
          // SubsAdmin can only count Orders of the Affiliate whose Subscribers must contain its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(), affiliateId);
        }

        return specificDao.countOrdersByAffiliateId(affiliateId);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Long countByChannelId(String channelId) throws BusinessException {
        logger.info("countByChannelId: " + channelId);

        // validate biz rules
        Channel channel = validationService.getChannel(channelId, false);

        // do authorization
          // Admin can count all Orders from the Channel
          // SubsAdmin can only count Orders from the Channel whose Subscriber must be its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = channel.getSubscriberId();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.countOrdersByChannelId(channelId);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Long countBySubscriberId(String subscriberId) throws BusinessException {
        logger.info("countBySubscriberId: " + subscriberId);

        // validate biz rules
        validationService.getSubscriber(subscriberId, false);

        // do authorization
          // Admin can count all Orders of the Subscriber
          // SubsAdmin can only count Orders of its Subscriber who must be the Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.countOrdersBySubscriberId(subscriberId);
    }

    @Override
    @Secured({SystemRole.ROLE_CHANNEL, SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderDto create(OrderCreateModel createModel) throws BusinessException {
        logger.info("create:" + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        List<String> infusionTags = createModel.getInfusionTags();
        Set<OrderLineCreateModel> orderLineCreateModels = createModel.getOrderLines();
        createModel.setOrderLines(null);
        Order newDO = beanMapper.map(createModel, Order.class);
        newDO.setOrderLines(new HashSet<>());
        newDO.setStatus(Order.STATUS_NEW);
        newDO.setInfusionTags(MyJsonUtil.gson.toJson(infusionTags));

        // validate biz rules
        String affiliateId = null;  // default: free Order
        Channel channel;
        String domainName;
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isChannel = SystemRole.hasChannelRole(loggedUserDto.getRoles());
        if (isChannel) {    // Order is pushed from Channel
            String nickname = createModel.getNickname();
            if (!StringUtils.isBlank(nickname)) {
                Affiliate affiliate = validationService.getAffiliateByNickname(nickname, false);
                affiliateId = affiliate.getId();
                newDO.setAffiliate(affiliate);
            }

            channel = validationService.getChannel(loggedUserDto.getId(), false);
            domainName = channel.getDomainName();
            newDO.setChannel(channel);
        }
        else {      // Order is created by other roles
            // Affiliate
            affiliateId = newDO.getAffiliateId();
            if (!StringUtils.isBlank(affiliateId)) {
                Affiliate affiliate = validationService.getAffiliate(affiliateId, false);
                newDO.setAffiliate(affiliate);
            }

            // Channel
            domainName = createModel.getChannelDomainName();
            channel = validationService.getChannelByDomainName(domainName, false);
            newDO.setChannel(channel);
        }

          // Order number exists in Channel
        Map<String, Object> orderParams = new HashMap<>();
        orderParams.put("number", newDO.getNumber());
        orderParams.put("channelId", channel.getId());
        Order existingOrder = genericDao.readUnique(Order.class, orderParams, false);
        if (existingOrder != null) {
            throw new BusinessException(BusinessException.ORDER_NUMBER_EXISTS_IN_CHANNEL,
                    String.format(BusinessException.ORDER_NUMBER_EXISTS_IN_CHANNEL_MESSAGE,
                                  newDO.getNumber(), domainName));
        }

          // Customer
        String email = newDO.getCustomer().getEmail();
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", email);
        customerParams.put("subscriberId", channel.getSubscriberId());
        Customer customer = genericDao.readUnique(Customer.class, customerParams, true);
        if (customer == null) {
            customer = newDO.getCustomer();
            customer.setSubscriber(channel.getSubscriber());
            customer.setFirstSellerId(affiliateId);
            genericDao.create(customer);
        }
        else {
            if (customer.getFirstSellerId() == null) {
                customer.setFirstSellerId(affiliateId);
            }
            MyBeanUtil.mapIgnoreNullProps(customer, newDO.getCustomer());
            genericDao.update(customer);
        }
        newDO.setCustomer(customer);

          // DiscountCode
        String code = createModel.getDiscountCode();
        if (!StringUtils.isBlank(code)) {
            DiscountCode discountCode = validationService.getDiscountCodeByAffiliateIdAndCode(affiliateId, code, false);
            Date today = MyDateUtil.convertToUTCDate(new Date());
            if (validateDate(discountCode.getStartDate(), today, discountCode.getEndDate())) {
                DiscountCodeApplied discountCodeApplied = beanMapper.map(discountCode, DiscountCodeApplied.class);
                discountCodeApplied.setId(null);    // make it new (non-detached)
                genericDao.create(discountCodeApplied);
                newDO.setDiscountCodeApplied(discountCodeApplied);
            }
        }

          // Input Aff vs Channel have the same Subscriber?
        if (affiliateId != null) {
            Map<String, Object> agentParams = new HashMap<>();
            agentParams.put("affiliateId", affiliateId);
            agentParams.put("subscriberId", channel.getSubscriberId());
            Agent agent = genericDao.readUnique(Agent.class, agentParams,false);
            if (agent == null) {   // Affiliate does not belong to Channel's Subscriber
                throw new BusinessException(BusinessException.ORDER_AFFILIATE_CHANNEL_NOT_SAME_SUBSCRIBER,
                        BusinessException.ORDER_AFFILIATE_CHANNEL_NOT_SAME_SUBSCRIBER_MESSAGE);
            }
        }

        // do authorization
          // SubsAdmin can only create an Order from Channel whose Subscriber is its Subscriber
        String subscriberId = channel.getSubscriberId();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        // do biz action
        newDO.setCreatedBy(loggedUserDto.getId());
        Double total = 0.0;
        newDO.setTotal(total);
        newDO.setIsCustomerConverted(Boolean.FALSE);
        Order pdo = genericDao.create(newDO);
        for (OrderLineCreateModel orderLineCreateModel: orderLineCreateModels) {
            OrderLine newOrderLine = beanMapper.map(orderLineCreateModel, OrderLine.class);
            ProductCreateModel productCreateModel = orderLineCreateModel.getProduct();
            productCreateModel.setChannelId(channel.getId());
            // validate view model
            String productErrors = productCreateModel.validate();
            if (productErrors != null) {
                throw new InvalidViewModelException(productErrors);
            }
            Map<String, Object> productParams = new HashMap<>();
            productParams.put("code", productCreateModel.getCode());
            productParams.put("channelId", channel.getId());
            Product product = genericDao.readUnique(Product.class, productParams, false);
            if (product == null) {  // Product not exist in db, create a new Product
                product = beanMapper.map(productCreateModel, Product.class);
                product.setChannel(channel);
                genericDao.create(product);
            }
            else {      // Product exists in db, update Product info
                MyBeanUtil.mapIgnoreNullProps(product, productCreateModel);
                genericDao.update(product);
            }

            // check COPP
            if (orderLineCreateModel.getCommission() != null) {
                newOrderLine.setCommission(MyNumberUtil.roundDouble(orderLineCreateModel.getCommission(), 2));
            }

            newOrderLine.setOrderId(pdo.getId());
            newOrderLine.setProduct(product);
            genericDao.create(newOrderLine);

            newDO.getOrderLines().add(newOrderLine);
            total += (newOrderLine.getPrice() * newOrderLine.getQuantity());
        }
        pdo.setTotal(total);
        genericDao.update(pdo);

        OrderDto dto = beanMapper.map(pdo, OrderDto.class);
        dto.setInfusionTags(infusionTags);

        return dto;
    }

    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public byte[] export(Date from, Date to) throws BusinessException {
        logger.info("export: " + " - from: " + from + " -> to: " + to);

        // create excel work book
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Orders");

        // create headers
        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Số đơn hàng");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Tên khách hàng");
        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Email khách hàng");
        Cell cell4 = row.createCell(3);
        cell4.setCellValue("Tổng tiền");
        Cell cell5 = row.createCell(4);
        cell5.setCellValue("Mã giảm giá");
        Cell cell6 = row.createCell(5);
        cell6.setCellValue("Giảm giá");
        Cell cell7 = row.createCell(6);
        cell7.setCellValue("Kênh");
        Cell cell8 = row.createCell(7);
        cell8.setCellValue("Người giới thiệu");

        // add Orders page by page
        SearchCriteria<OrderSearchCriteria> searchCriteria = new SearchCriteria<>();
        int pageSize = 1000;
        int startRow = 1;
        searchCriteria.setCriteria(new OrderSearchCriteria());
        searchCriteria.setPageIndex(1);
        searchCriteria.setPageSize(pageSize);

        // do authorization
            // SubsAdmin can export Orders of its Subscriber
            // Affiliate can only export its Orders
        CustomCriteria customCriteria = new CustomCriteria();
        customCriteria.setValue("createdAt>=", from);
        customCriteria.setValue("createdAt<=", to);

        // render first page
        searchCriteria.setCustomCriteria(customCriteria);
        SearchResult<OrderDto> searchResult = this.search(searchCriteria);      // this method does authorization
        logger.info("Export Page 1: " + searchResult.getList().size() + " items");
        this.renderOrdersToExcelSheet(searchResult.getList(), sheet, startRow);
        // render the rest
        int numberOfPages = searchResult.getNumOfPages();
        for (int pageIndex = 2; pageIndex <= numberOfPages; pageIndex++) {
            searchCriteria.setPageIndex(pageIndex);
            searchResult = this.search(searchCriteria);
            logger.info("Export Page " + pageIndex + ": " + searchResult.getList().size() + " items");
            startRow += pageSize;
            this.renderOrdersToExcelSheet(searchResult.getList(), sheet, startRow);
        }

        // write excel work book to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        }
        catch (Exception ex) {
            throw new RuntimeException("Can not write data to output stream");
        }
        finally {
            try {
                workbook.close();
                bos.close();
            } catch (IOException e) {
            }
        }
        byte[] contents = bos.toByteArray();

        return contents;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    public OrderDto get(String id) throws BusinessException {
        logger.info("get: " + id);

        // validate biz rules
        Order pdo = validationService.getOrder(id, false);

        // do authorization
          /// SubsAdmin can only get Order of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getChannel().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // eagerly process OrderLines
        MyBeanUtil.triggerLazyLoad(pdo.getOrderLines());

        OrderDto dto = beanMapper.map(pdo, OrderDto.class);
        if (StringUtils.isNotBlank(pdo.getInfusionTags())) {
            dto.setInfusionTags((List<String>) MyJsonUtil.gson.fromJson(pdo.getInfusionTags(), List.class));
        }
        return dto;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public Double getApprovedRevenue() throws BusinessException {
        return getRevenue(Order.STATUS_APPROVED);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Double getApprovedRevenueByChannelId(String channelId) throws BusinessException {
        return getRevenueByChannelId(channelId, Order.STATUS_APPROVED);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Double getApprovedRevenueBySubscriberId(String subscriberId) throws BusinessException {
        return getRevenueBySubscriberId(subscriberId, Order.STATUS_APPROVED);
    }

    @Override
    public Double getCommissionsDoneRevenue() throws BusinessException {
        return getRevenue(Order.STATUS_COMMISSIONS_DONE);
    }

    @Override
    public Double getCommissionsDoneRevenueByChannelId(String channelId) throws BusinessException {
        return getRevenueByChannelId(channelId, Order.STATUS_COMMISSIONS_DONE);
    }

    @Override
    public Double getCommissionsDoneRevenueBySubscriberId(String subscriberId) throws BusinessException {
        return getRevenueBySubscriberId(subscriberId, Order.STATUS_COMMISSIONS_DONE);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public Double getNewRevenue() throws BusinessException {
        return getRevenue(Order.STATUS_NEW);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Double getNewRevenueByChannelId(String channelId) throws BusinessException {
        return getRevenueByChannelId(channelId, Order.STATUS_NEW);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Double getNewRevenueBySubscriberId(String subscriberId) throws BusinessException {
        return getRevenueBySubscriberId(subscriberId, Order.STATUS_NEW);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public Double getRejectedRevenue() throws BusinessException {
        return getRevenue(Order.STATUS_REJECTED);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Double getRejectedRevenueByChannelId(String channelId) throws BusinessException {
        return getRevenueByChannelId(channelId, Order.STATUS_REJECTED);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Double getRejectedRevenueBySubscriberId(String subscriberId) throws BusinessException {
        return getRevenueBySubscriberId(subscriberId, Order.STATUS_REJECTED);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public Double getRevenue(String status) throws BusinessException {
        logger.info("getRevenue: " + status);

        // do authorization
          // Admin can get Revenue from all Orders
          // SubsAdmin can only get Revenue from Orders of its Subscriber
          // Affiliate can only get Revenue from its Orders
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            return specificDao.getOrderRevenueBySubscriberId(loggedSubsAdmin.getSubscriberId(), status);
        }
        else if (isAffiliate) {
            return specificDao.getOrderRevenueByAffiliateId(loggedUserDto.getId(), status);
        }

        return specificDao.getOrderRevenue(status);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Double getRevenueByAffiliateId(String affiliateId, String status) throws BusinessException {
        logger.info("getRevenueByAffiliateId: " + affiliateId + " - status: " + status);

        // validate biz rules
        validationService.getAffiliate(affiliateId, false);

        // do authorization
          // Admin can get Revenue from all Orders from the Affiliate
          // SubsAdmin can only get Revenue from Orders from the Affiliate whose Subscribers must contain its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(), affiliateId);
        }

        return specificDao.getOrderRevenueByAffiliateId(affiliateId, status);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Double getRevenueByChannelId(String channelId, String status) throws BusinessException {
        logger.info("getRevenueByChannelId: " + channelId + " - status: " + status);

        // validate biz rules
        Channel channel = validationService.getChannel(channelId, false);

        // do authorization
          // Admin can get Revenue from all Orders from the Channel
          // SubsAdmin can only get Revenue from Orders from the Channel whose Subscriber must be its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = channel.getSubscriberId();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.getOrderRevenueByChannelId(channelId, status);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Double getRevenueBySubscriberId(String subscriberId, String status) throws BusinessException {
        logger.info("getRevenueBySubscriberId: " + subscriberId + " - status: " + status);

        // validate biz rules
        validationService.getSubscriber(subscriberId, false);

        // do authorization
          // Admin can get Revenue from all Orders
          // SubsAdmin can only get Revenue from Orders of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.getOrderRevenueBySubscriberId(subscriberId, status);
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderDto reject(String id, String reason) throws BusinessException {
        logger.info("reject: " + id + " with reason: " + reason);

        // validate biz rules
        Order pdo = validationService.getOrder(id, true);

        // do authorization
            // SubsAdmin can only reject the Order of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = pdo.getChannel().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // do biz action
        changeStatusOnAction(Order.ACTION_REJECT, pdo);
        pdo.setReason(reason);
        genericDao.update(pdo);

        return beanMapper.map(pdo, OrderDto.class);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_SUBS_ADMIN})
    public SearchResult<OrderDto> search(SearchCriteria<OrderSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Order> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);   // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Order criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Order.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Order());
        }

        // process custom criteria
        SearchResult<Order> searchResult;
        SearchResult<OrderDto> dtoSearchResult;
        CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
        List<String> orderIds = new ArrayList<>();

        // by productName and subscriberId
        if(customCriteria.keySet().contains("productName") && customCriteria.keySet().contains("subscriberId")) {
            String productName = customCriteria.getValue("productName");
            String subscriberId = customCriteria.getValue("subscriberId");
            List<String> orderIdsByProductNameAndSubscriberId = specificDao.getOrderIdsByProductNameAndSubscriberId(productName,subscriberId);
            if (orderIdsByProductNameAndSubscriberId.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            customCriteria.remove("productName");
            customCriteria.remove("subscriberId");
            orderIds.addAll(orderIdsByProductNameAndSubscriberId);
        }

        // by productName only
         else if (customCriteria.keySet().contains("productName")) {
            String productName = customCriteria.getValue("productName");
            List<String> orderIdsByProductName = specificDao.getOrderIdsByProductName(productName);
            if (orderIdsByProductName.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            customCriteria.remove("productName");
            orderIds.addAll(orderIdsByProductName);
        }

        // by subscriberId only
        else if (customCriteria.keySet().contains("subscriberId")) {
            String subscriberId = customCriteria.getValue("subscriberId");
            List<String> orderIdsBySubscriberId = specificDao.getOrderIdsBySubscriberId(subscriberId);
            if (orderIdsBySubscriberId.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            customCriteria.remove("subscriberId");
            orderIds.addAll(orderIdsBySubscriberId);
        }

        // do authorization
          // Admin can search all Orders
          // SubsAdmin can search Orders of its Subscriber
          // Affiliate can only search its Orders
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isAdmin = SystemRole.hasAdminRole(loggedUserDto.getRoles());
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());

        List<String> authOrderIds = new ArrayList<>();
        if (!isAdmin) {
            if (isSubsAdmin) {
                SubsAdmin loggedSubsAdmin = validationService.getSubsAdmin(loggedUserDto.getId(),false);
                authOrderIds = specificDao.getOrderIdsBySubscriberId(loggedSubsAdmin.getSubscriberId());
            } else if (isAffiliate) {
                authOrderIds = specificDao.getOrderIdsByAffiliateId(loggedUserDto.getId());
            }

            if (authOrderIds.size() == 0) {
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createDtoSearchResult(searchResult);
                return dtoSearchResult;
            }

            if (orderIds.size() > 0) {  // search by product name
                orderIds.retainAll(authOrderIds);
                if (orderIds.size() == 0) {
                    searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                    dtoSearchResult = createDtoSearchResult(searchResult);
                    return dtoSearchResult;
                }
            } else {
                orderIds.addAll(authOrderIds);
            }
        }

        if (orderIds.size() > 0) {
            customCriteria.setValue("id", orderIds);
        }

        // do biz action
        searchResult = genericDao.search(searchCriteria);
        triggerLazyLoad(searchResult.getList());
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL})
    public OrderDto sendDataToGetfly(OrderDto orderDto) throws BusinessException {
        logger.info("sendDataToGetfly: " + orderDto);

        SubsGetflyConfig subsGetflyConfig = validationService.getSubsGetflyConfig(orderDto.getChannel().getSubscriberId(), false);
        if (subsGetflyConfig.getApiKey() == null || subsGetflyConfig.getBaseUrl() == null) {
            throw new BusinessException(BusinessException.SUBS_GETFLY_CONFIG_BASEURL_APIKEY_NOT_FOUND,
                    String.format(BusinessException.SUBS_GETFLY_CONFIG_BASEURL_APIKEY_NOT_FOUND_MESSAGE,
                            subsGetflyConfig.getId()));
        }

        String customerEmail = orderDto.getCustomer().getEmail();
        String baseUrl = subsGetflyConfig.getBaseUrl();
        String apiKey = subsGetflyConfig.getApiKey();
        try {
            GetflyAccount account = getflyClient.getAccountByEmail(customerEmail, baseUrl, apiKey);
            if (account == null) {
                account = new GetflyAccount();
            }

            boolean success = getflyClient.createOrder(orderDto, baseUrl, apiKey, account.getCode());
            if (success) {
                this.updateGetflySuccess(orderDto.getId(), true);
                orderDto.setIsGetflySuccess(true);
            }
            else {
                this.updateGetflySuccess(orderDto.getId(), false);
                orderDto.setIsGetflySuccess(false);
            }
        }
        catch (GetflyIntegrationException gie) {
            logger.error("sendDataToGetfly failed: " + gie.getMessage(), gie);
        }

        return orderDto;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL})
    public OrderDto sendDataToGetResponse(OrderDto orderDto) throws BusinessException {
        logger.info("sendDataToGetResponse: " + orderDto);

        SubsGetResponseConfig subsGetResponseConfig = validationService.getSubsGetResponseConfig(orderDto.getChannel().getSubscriberId(), false);
        if (subsGetResponseConfig.getApiKey() == null || subsGetResponseConfig.getCampaignDefaultId() == null) {
            throw new BusinessException(BusinessException.SUBS_GETRESPONSE_CONFIG_CAMPAIGN_DEFAULT_APIKEY_NOT_FOUND,
                    String.format(BusinessException.SUBS_GETRESPONSE_CONFIG_CAMPAIGN_DEFAULT_APIKEY_NOT_FOUND_MESSAGE,
                            subsGetResponseConfig.getId()));
        }

        CustomerDto customerDto = orderDto.getCustomer();
        String customerEmail = customerDto.getEmail();
        String apiKey = subsGetResponseConfig.getApiKey();
        SubsGetResponseConfigDto subsGetResponseConfigDto = beanMapper.map(subsGetResponseConfig, SubsGetResponseConfigDto.class);
        try {
            GetResponseContact contact = getResponseClient.getContactByEmail(customerEmail, apiKey);
            if (contact != null && contact.getId() != null) {
                this.updateGetResponseSuccess(orderDto.getId(), true);
                orderDto.setIsGetResponseSuccess(true);
                return orderDto;
            }

            boolean success = getResponseClient.createContactFromCustomerDto(customerDto, apiKey, subsGetResponseConfigDto);
            if (success) {
                this.updateGetResponseSuccess(orderDto.getId(), true);
                orderDto.setIsGetResponseSuccess(true);
            }
            else {
                this.updateGetResponseSuccess(orderDto.getId(), false);
                orderDto.setIsGetResponseSuccess(false);
            }
        }
        catch (GetResponseIntegrationException grie) {
            logger.error("sendDataToGetResponse failed: " + grie.getMessage(), grie);
        }

        return orderDto;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_CHANNEL})
    public OrderDto sendDataToInfusion(OrderDto orderDto) throws BusinessException, IOException {
        logger.info("sendDataToInfusion: " + orderDto);

        SubsInfusionConfig subsInfusionConfig = validationService.getSubsInfusionConfig(orderDto.getChannel().getSubscriberId(), false);
        if (subsInfusionConfig.getAccessToken() == null) {
            throw new BusinessException(BusinessException.SUBS_INFUSION_CONFIG_ACCESS_TOKEN_UNAUTHORIZED,
                    String.format(BusinessException.SUBS_INFUSION_CONFIG_ACCESS_TOKEN_UNAUTHORIZED_MESSAGE,
                            subsInfusionConfig.getId()));
        }

        String accessToken = subsInfusionConfigService.getAccessToken(subsInfusionConfig.getId());
        try {
            InfusionContact contact = infusionClient.getContactByEmail(orderDto.getCustomer().getEmail(), accessToken);
            if (contact == null || contact.getId() == null) {
                contact = infusionClient.createContact(orderDto.getCustomer(), accessToken);
            }

            boolean applyTagsSuccess = true;
            if (orderDto.getInfusionTags() != null && orderDto.getInfusionTags().size() > 0) {
                List<String> tagIds = new ArrayList<>();
                for (String tagName : orderDto.getInfusionTags()) {
                    InfusionTag tag = infusionClient.getTagByName(tagName, accessToken);
                    if (tag != null && tag.getId() != null) {
                        tagIds.add(tag.getId());
                    }
                }
                if (tagIds.size() > 0) {
                    applyTagsSuccess = infusionClient.applyTagsToContact(contact.getId(), tagIds, accessToken);
                }
            }

            if (contact != null && contact.getId() != null && applyTagsSuccess) {
                this.updateInfusionSuccess(orderDto.getId(), true);
                orderDto.setIsInfusionSuccess(true);
            }
            else {
                this.updateInfusionSuccess(orderDto.getId(), false);
                orderDto.setIsInfusionSuccess(false);
            }
        }
        catch (InfusionIntegrationException iie) {
            logger.error("sendDataToInfusion failed: " + iie.getMessage(), iie);
        }

        return orderDto;
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderDto update(String id, OrderUpdateModel updateModel) throws BusinessException {
        logger.info("update: " + id + " - " + updateModel);

        // process view model
        updateModel.escapeHtml();
        String errors = updateModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Order detachedDO = beanMapper.map(updateModel, Order.class);

        // validate biz rules
        Order existingDO = validationService.getOrder(id, true);
        if (!existingDO.getStatus().equals(Order.STATUS_NEW)) {
            throw new BusinessException(BusinessException.ORDER_CAN_NOT_UPDATE_NON_NEW_ORDER,
                    BusinessException.ORDER_CAN_NOT_UPDATE_NON_NEW_ORDER_MESSAGE);
        }

        if (detachedDO.getNumber() != null && !detachedDO.getNumber().equals(existingDO.getNumber())) {
            Map<String, Object> orderParams = new HashMap<>();
            orderParams.put("number", detachedDO.getNumber());
            orderParams.put("channelId", existingDO.getChannel().getId());
            Order existingOrder = genericDao.readUnique(Order.class, orderParams, false);
            if (existingOrder != null) {
                throw new BusinessException(BusinessException.ORDER_NUMBER_EXISTS_IN_CHANNEL,
                        String.format(BusinessException.ORDER_NUMBER_EXISTS_IN_CHANNEL_MESSAGE,
                                      detachedDO.getNumber(), existingDO.getChannelId() ));
            }
        }

        // do authorization
          // SubsAdmin who created the Order can update it
        UserDto loggedUserDto = authenService.getLoggedUser();
        if (!loggedUserDto.getId().equals(existingDO.getCreatedBy())) {
            throw new BusinessException(BusinessException.ORDER_WRONG_ORDER_CREATOR,
                                        BusinessException.ORDER_WRONG_ORDER_CREATOR_MESSAGE);
        }

        // map input to existingDO
        MyBeanUtil.mapIgnoreNullProps(existingDO, detachedDO);

        // do biz action
        genericDao.update(existingDO);

        return beanMapper.map(existingDO, OrderDto.class);
    }

    @Override
    @Transactional(value = "jdbcTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateGetflySuccess(String id, boolean success) {
        logger.info("updateGetflySuccess: " + id + " success: " + success);

        int numberOfRowsAffected = specificDao.updateGetflySuccess(id, success);
        if (numberOfRowsAffected == 0) {
            logger.error("Order '" + id + "' not found !");
        }
    }

    @Override
    @Transactional(value = "jdbcTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateGetResponseSuccess(String id, boolean success) {
        logger.info("updateGetResponseSuccess: " + id + " success: " + success);

        int numberOfRowsAffected = specificDao.updateGetResponseSuccessOfOrder(id, success);
        if (numberOfRowsAffected == 0) {
            logger.error("Order '" + id + "' not found !");
        }
    }

    @Override
    @Transactional(value = "jdbcTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateInfusionSuccess(String id, boolean success) {
        logger.info("updateInfusionSuccess: " + id + " success: " + success);

        int numberOfRowsAffected = specificDao.updateInfusionSuccess(id, success);
        if (numberOfRowsAffected == 0) {
            logger.error("Order '" + id + "' not found !");
        }
    }

    @Override
    @Secured({SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_SUBS_ADMIN})
    public OrderViewDto view(String id) throws BusinessException {
        logger.info("view: " + id);

        // validate biz rules
        Order order = validationService.getOrder(id, false);
        MyBeanUtil.triggerLazyLoad(order.getOrderLines());

        // do authorization
          // SubsAdmin can only view the Order that belongs to its Subscriber
          // Affiliate can only view its Order
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = order.getChannel().getSubscriberId();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }
        else if (isAffiliate) {
            if (!loggedUserDto.getId().equals(order.getAffiliateId())) {
                throw new BusinessException(BusinessException.ORDER_AFFILIATE_DATA_AUTHORIZATION,
                        String.format(BusinessException.ORDER_AFFILIATE_DATA_AUTHORIZATION_MESSAGE, order.getId()));
            }
        }

        // do biz action
        OrderViewDto dto = new OrderViewDto();
        dto.setOrder(beanMapper.map(order, OrderDto.class));
        if (order.getAffiliate() != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("orderId", id);
            List<Commission> commissions = genericDao.list(Commission.class, params);
            commissions.forEach(commission -> {
                CommissionDto commissionDto = beanMapper.map(commission, CommissionDto.class);
                dto.getCommissions().add(commissionDto);
            });
        }

        return dto;
    }


    // Utilities
    private void changeStatusOnAction(String action, Order order) throws BusinessException {
        String fromStatus = order.getStatus();
        String toStatus = Order.Action2StatusMap.get(action);
        switch (action) {
            case Order.ACTION_APPROVE:
                if (!isStatusChangeAllowed(fromStatus, toStatus)) {
                    throw new BusinessException(BusinessException.ORDER_APPROVE_ERROR,
                            String.format(BusinessException.ORDER_APPROVE_ERROR_MESSAGE, order.getStatus()));
                }
                break;

            case Order.ACTION_CONFIRM_COMMISSIONS_DONE:
                if (!isStatusChangeAllowed(fromStatus, toStatus)) {
                    throw new BusinessException(BusinessException.ORDER_CONFIRM_COMMISSIONS_DONE_ERROR,
                            String.format(BusinessException.ORDER_CONFIRM_COMMISSIONS_DONE_ERROR_MESSAGE, order.getStatus()));
                }
                break;

            case Order.ACTION_REJECT:
                if (!isStatusChangeAllowed(fromStatus, toStatus)) {
                    throw new BusinessException(BusinessException.ORDER_REJECT_ERROR,
                            String.format(BusinessException.ORDER_REJECT_ERROR_MESSAGE, order.getStatus()));
                }
                break;

            default:
                throw new BusinessException(BusinessException.ORDER_INVALID_ACTION,
                        String.format(BusinessException.ORDER_INVALID_ACTION_MESSAGE, action));
        }
        order.setStatus(toStatus);
    }

    private SearchResult<OrderDto> createDtoSearchResult(SearchResult<Order> searchResult) {
        SearchResult<OrderDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<OrderDto> dtos = new ArrayList<>();
        for (Order pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, OrderDto.class));
        }
        result.setList(dtos);

        return result;
    }

    private boolean isStatusChangeAllowed(String fromStatus, String toStatus) {
        if (StringUtils.isBlank(fromStatus) || StringUtils.isBlank(toStatus)) {
            return false;
        }

        fromStatus = fromStatus.toUpperCase();
        toStatus = toStatus.toUpperCase();
        String allowedStatuses = Order.StatusFlow.get(fromStatus);

        return allowedStatuses != null && allowedStatuses.contains(toStatus);
    }

    private void renderOrdersToExcelSheet(List<OrderDto> orderDtos, XSSFSheet sheet, int startRow) {
        for (OrderDto orderDto : orderDtos) {
            Row row = sheet.createRow(startRow++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(orderDto.getNumber());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(orderDto.getCustomer().getFullname());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(orderDto.getCustomer().getEmail());
            Cell cell4 = row.createCell(3);
            cell4.setCellValue(orderDto.getTotal());
            if (orderDto.getDiscountCodeApplied() != null) {
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(orderDto.getDiscountCodeApplied().getCode());
                DecimalFormat df = new DecimalFormat("##.##");
                Cell cell6 = row.createCell(5);
                cell6.setCellValue(df.format(orderDto.getDiscountCodeApplied().getDiscount()));
            }
            Cell cell7 = row.createCell(6);
            cell7.setCellValue(orderDto.getChannel().getDomainName());
            if (orderDto.getAffiliate() != null) {
                Cell cell8 = row.createCell(7);
                cell8.setCellValue(orderDto.getAffiliate().getNickname());
            }

            for (OrderLineDto orderLineDto : orderDto.getOrderLines()) {
                row = sheet.createRow(startRow++);
                Cell orderLineCell1 = row.createCell(1);
                orderLineCell1.setCellValue(orderLineDto.getProduct().getName());
                Cell orderLineCell2 = row.createCell(2);
                orderLineCell2.setCellValue(orderLineDto.getQuantity());
                Cell orderLineCell3 = row.createCell(3);
                orderLineCell3.setCellValue(orderLineDto.getPrice());
            }
        }
    }

    private void triggerLazyLoad (List<Order> orders) {
        for (Order order: orders) {
            for (OrderLine orderLine: order.getOrderLines()) {
                MyBeanUtil.triggerLazyLoad(orderLine.getProduct());
            }
        }
    }

    private boolean validateDate (Date startDate, Date date, Date endDate) {
        if (date == null) {
            return false;
        }

        if ( startDate != null && endDate != null ) {
            if ( !startDate.after(date) && !date.after(endDate) ) {
                return true;
            }
        }
        else if ( startDate != null) {
            if ( !startDate.after(date) ) {
                return true;
            }
        }
        else if ( endDate != null) {
            if ( !date.after(endDate) ) {
                return true;
            }
        }
        else {
            return true;
        }

        return false;
    }
}
