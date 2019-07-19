package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.*;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyDateUtil;
import com.rms.rms.common.util.MyNumberUtil;
import com.rms.rms.common.view_model.CommissionCheckModel;
import com.rms.rms.common.view_model.CommissionSearchCriteria;
import com.rms.rms.common.view_model.OrderLineCheckModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
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
public class CommissionServiceImpl implements CommissionService {

    private Logger logger = Logger.getLogger(CommissionServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderDto calculateCommissions(String orderId) throws BusinessException {
        logger.info("calculateCommissions: " + orderId);

        // validate biz rules
        Order order = validationService.getOrder(orderId, false);
        if (!order.getStatus().equals(Order.STATUS_APPROVED)) {
            throw new BusinessException(BusinessException.ORDER_STATUS_INVALID_FOR_COMMISSION_CALCULATION,
                    String.format(BusinessException.ORDER_STATUS_INVALID_FOR_COMMISSION_CALCULATION_MESSAGE, order.getStatus()));
        }

        // do authorization
          /// SubsAdmin can only calculate Commissions on the Order of its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        String subscriberId = order.getChannel().getSubscriberId();
        authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);

        // calculate Commissions on the Order
        Subscriber subscriber = genericDao.read(Subscriber.class, order.getChannel().getSubscriberId(), false);
        Map<String, Object> scgParams = new HashMap<>();
        scgParams.put("isEnabled", Boolean.TRUE);
        scgParams.put("subscriberId", subscriber.getId());

        // 1. CFE used to determine Seller
        String sellerId = order.getAffiliateId();
        String firstSellerId = order.getCustomer().getFirstSellerId();
          // This is a free Order and its Customer does not have first seller -> no one earns Commissions from it
        if (sellerId == null && firstSellerId == null) {
            return beanMapper.map(order, OrderDto.class);
        }
          // This is a free Order & its Customer has first seller -> check if CFE is configured
        if (firstSellerId != null) {
            scgParams.put("type", SubsCommissionConfig.TYPE_CFE);
            SubsCommissionConfig scgCFE = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);
            if (scgCFE != null) {   // CFE is configured -> assign seller to firstSeller
                sellerId = firstSellerId;
            } else if (sellerId == null) {
                return beanMapper.map(order, OrderDto.class);
            }
        }
          // This is a pulled Order & its Customer does not have first seller -> assign its Customer's firstSeller to the seller
        else {
            Customer customer = validationService.getCustomer(order.getCustomerId(), true);
            customer.setFirstSellerId(sellerId);
            genericDao.update(customer);
        }

          // get seller info
        Affiliate seller = genericDao.read(Affiliate.class, sellerId, false);

          // get SubsCommissionConfigs
        scgParams.put("type", SubsCommissionConfig.TYPE_COPPR);
        SubsCommissionConfig scgCOPPR = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_CAD);
        SubsCommissionConfig scgCAD = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_COAN);
        SubsCommissionConfig scgCOAN = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_COOV);
        SubsCommissionConfig scgCOOV = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_COPG);
        SubsCommissionConfig scgCOPG = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_COPS);
        SubsCommissionConfig scgCOPS = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_COPQ);
        SubsCommissionConfig scgCOPQ = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

          // init sellerEarning, orderTotal and orderEarning
        double sellerEarning = 0d;
        double orderTotal = order.getTotal().doubleValue();
        double orderEarning = 0d;

        // 2,3,4,5 scan Products to apply COPP or COPS or COPPR or CAD
        for (OrderLine orderLine : order.getOrderLines()) {
            Double productCommission = orderLine.getCommission();
            String productId = orderLine.getProductId();
            double productPrice = orderLine.getPrice().doubleValue();
            double productQuantity = orderLine.getQuantity().doubleValue();
            boolean hasCOPP = false;
            boolean hasCOPS = false;
            boolean hasCOPPR = false;

            // check COPP
            if (productCommission != null && productCommission.doubleValue() > 0) {
                orderEarning += productCommission.doubleValue() * productPrice * productQuantity;
                hasCOPP = true;
            }

            // no have COPP, check COPS
            if (!hasCOPP && scgCOPS != null) {
                List<ProductSet> pss = findProductSets(scgCOPS, productId);
                for (ProductSet ps : pss) {
                    orderEarning += ps.getCommission() * productPrice * productQuantity;
                    hasCOPS = true;
                }
            }

            // neither have COPP nor COPS, check COPPR
            if (!hasCOPP && !hasCOPS && scgCOPPR != null) {
                ConditionCommissionConfig ccc = findConditionCommissionConfig(scgCOPPR, productPrice, null);
                if (ccc != null) {
                    orderEarning += ccc.getCommission().doubleValue() * productPrice * productQuantity;
                    hasCOPPR = true;
                }
            }

            // neither have COPP nor COPS nor COPPR, check CAD
            if (!hasCOPP && !hasCOPS && !hasCOPPR && scgCAD != null) {
                Double cadCommission = scgCAD.getCommission();
                if(cadCommission != null && cadCommission.doubleValue() > 0) {
                    orderEarning += cadCommission.doubleValue() * productPrice * productQuantity;
                }
            }
        }

        // update Order earning
        order.setEarning(Double.valueOf(orderEarning));
        genericDao.update(order);

        // 6. process COAN for seller and its ancestors (TOWER) or its managers (LEVEL)
        if (scgCOAN == null) {  // orderEarning is not divided to seller's ancestors, it earns all orderEarning
            createCommission(Commission.TYPE_CBS, Double.valueOf(1d), Double.valueOf(orderEarning),
                    seller, subscriber, orderId, null, null, null, null);
            sellerEarning += orderEarning;
        }
        else if (subscriber.getSubsConfig().getCoanType().equals(SubsConfig.TYPE_COAN_TOWER)) {
            // create CBS for seller at layer = 1
            int layer = 1;
            Double cbsCommission = getCommissionByLayer(scgCOAN.getLayerCommissionConfigs(), layer);
            if (cbsCommission != null && cbsCommission.doubleValue() > 0) {
                double cbsEarning = orderEarning * cbsCommission.doubleValue();
                createCommission(Commission.TYPE_CBS, cbsCommission, Double.valueOf(cbsEarning),
                        seller, subscriber, orderId, scgCOAN, null, null, null);
                sellerEarning += cbsEarning;
            }

            // create COAN for its ancestors at layer = 2,3...
            layer++;
            String affiliateId = sellerId;
            Affiliate ancestor = null;
            Map<String, Object> agentParams = new HashMap<>();
            do {
                // init affiliateId
                if (ancestor != null) {
                    affiliateId = ancestor.getId();
                }

                // get agent
                agentParams.put("subscriberId", subscriberId);
                agentParams.put("affiliateId", affiliateId);
                Agent agent = genericDao.readUnique(Agent.class, agentParams, false);
                if (agent != null && agent.getInheritor() != null) {
                    // get ancestor
                    Map<String, Object> ancestorParams = new HashMap<>();
                    ancestorParams.put("nickname", agent.getInheritor());
                    ancestor = genericDao.readUnique(Affiliate.class, ancestorParams, false);
                    if (ancestor != null) {
                        // get Commission by layer
                        cbsCommission = getCommissionByLayer(scgCOAN.getLayerCommissionConfigs(), layer);

                        // create COAN for ancestor
                        if (cbsCommission != null && cbsCommission.doubleValue() > 0) {
                            double coanEarning = orderEarning * cbsCommission.doubleValue();
                            createCommission(Commission.TYPE_COAN, cbsCommission, Double.valueOf(coanEarning),
                                    ancestor, subscriber, orderId, scgCOAN, null, null, null);
                            increaseEarningBalance(ancestor.getId(), subscriberId, coanEarning);
                        }

                        layer++;
                    }
                }
                else {  // inheritor not found, reach top of the Affiliate network, set stop condition
                    ancestor = null;
                }
            }
            while (ancestor != null);
        }
        else if (subscriber.getSubsConfig().getCoanType().equals(SubsConfig.TYPE_COAN_LEVEL)) {
            logger.error("LEVEL not yet supported");
            // support this case later
        }
        else {
            logger.error("Unsupported COAN type: " + subscriber.getSubsConfig().getCoanType());
        }

        // 7. find COOV
        if (scgCOOV != null) {     // COOV found
            ConditionCommissionConfig ccc = findConditionCommissionConfig(scgCOOV, orderTotal, null);
            if (ccc != null) {
                double scgEarning = ccc.getCommission() * orderTotal;
                createCommission(Commission.TYPE_COOV, ccc.getCommission(), Double.valueOf(scgEarning),
                                 seller, subscriber, orderId, scgCOOV, ccc, null, null);
                sellerEarning += scgEarning;
            }
        }

        // 8. find COPGs
        if (scgCOPG != null) {    // COPG found
            List<PriorityGroup> pgs = findPriorityGroups(scgCOPG, sellerId);
            for (PriorityGroup pg : pgs) {
                double scgEarning = pg.getCommission() * orderTotal;
                createCommission(Commission.TYPE_COPG, pg.getCommission(), Double.valueOf(scgEarning),
                                 seller, subscriber, orderId, scgCOPG, null, pg, null);
                sellerEarning += scgEarning;
            }
        }

        // 9. apply DiscountCode if any
        DiscountCodeApplied dca = order.getDiscountCodeApplied();
        if (dca != null) {          // CDC found
            double dcaValue = 0 - dca.getDiscount() * orderTotal;
            createCommission(Commission.TYPE_CDC, dca.getDiscount(), Double.valueOf(dcaValue),
                             seller, subscriber, orderId, null, null, null, dca);
            sellerEarning += dcaValue;
        }

        // 10. find COPQ
        if (scgCOPQ != null) {     // COPQ found
            for (OrderLine orderLine : order.getOrderLines()) {
                String productId = orderLine.getProductId();
                double productPrice = orderLine.getPrice();
                double productQuantity = orderLine.getQuantity().doubleValue();
                ConditionCommissionConfig ccc = findConditionCommissionConfig(scgCOPQ, productQuantity, productId);
                if (ccc != null) {
                    double scgEarning = ccc.getCommission() * productPrice * productQuantity;
                    createCommission(Commission.TYPE_COPQ, ccc.getCommission(), scgEarning,
                            seller, subscriber, orderId, scgCOPQ, ccc, null, null);
                    sellerEarning += scgEarning;
                }
            }
        }

        increaseEarningBalance(sellerId, subscriberId, sellerEarning);

        // confirm Commissions done for Order
        orderService.confirmCommissionsGeneratedDone(orderId);

        return beanMapper.map(order, OrderDto.class);
    }

    @Override
    @Secured(SystemRole.ROLE_SUBS_ADMIN)
    public double calculateEarningByAffiliateIdAndOrderId(String affiliateId, String orderId) throws BusinessException {
        logger.info("calculateEarningByAffiliateIdAndOrderId: " + affiliateId + ", " + orderId);

        // validate biz rule
        Affiliate affiliate = validationService.getAffiliate(affiliateId,false);
        Order order = validationService.getOrder(orderId,false);
        if (!order.getStatus().equals(Order.STATUS_COMMISSIONS_DONE)) {
            throw new BusinessException(BusinessException.ORDER_STATUS_INVALID_FOR_COMMISSION_CALCULATION,
                    String.format(BusinessException.ORDER_STATUS_INVALID_FOR_COMMISSION_CALCULATION_MESSAGE, order.getStatus()));
        }

        // do authorization
        UserDto loggedUserDto = authenService.getLoggedUser();
        authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(),affiliate.getId());

        // do biz action
        return specificDao.getCommissionEarningByAffiliateIdAndOrderId(affiliateId,orderId);
    }

    @Override
    @Secured({SystemRole.ROLE_CHANNEL})
    public CommissionExperienceDto checkCommissions(CommissionCheckModel checkModel) throws BusinessException {
        logger.info("checkCommissions: " + checkModel);

        // process view model
        checkModel.escapeHtml();
        String errors = checkModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Set<OrderLineCheckModel> orderLineCheckModels = checkModel.getOrderLines();
        Double discount = checkModel.getDiscount();
        String affiliateId = checkModel.getAffiliateId();
        String customerEmail = checkModel.getCustomerEmail();

        // validate biz rules
        UserDto loggedUserDto = authenService.getLoggedUser();
        Channel channel = validationService.getChannel(loggedUserDto.getId(), false);

        // do authorization

        // init dto
        CommissionExperienceDto dto = new CommissionExperienceDto();
        dto.setCbs(0d);
        dto.setCoanReferrer1(0d);
        dto.setCoanReferrer2(0d);
        dto.setCoov(0d);
        dto.setCopg(0d);
        dto.setCdc(0d);
        dto.setCopq(0d);

        // calculate Commissions on the Order
        Subscriber subscriber = genericDao.read(Subscriber.class, channel.getSubscriberId(), false);
        Map<String, Object> scgParams = new HashMap<>();
        scgParams.put("isEnabled", Boolean.TRUE);
        scgParams.put("subscriberId", subscriber.getId());

        // 1. CFE used to determine Seller
        if (customerEmail != null) {
            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("email", customerEmail);
            customerParams.put("subscriberId", subscriber.getId());
            Customer customer = genericDao.readUnique(Customer.class, customerParams, false);
            if (customer != null && customer.getFirstSellerId() != null) {
                scgParams.put("type", SubsCommissionConfig.TYPE_CFE);
                SubsCommissionConfig scgCFE = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);
                if (scgCFE != null) {   // CFE is configured
                    dto.setMessage("CFE is configured, this Affiliate does not earns Commission!");
                    return dto;
                }
            }
        }

        // get package configs of the Subscriber
        PackageConfigApplied pca = subscriber.getPackageConfigApplied();

        // get SubsCommissionConfigs
        scgParams.put("type", SubsCommissionConfig.TYPE_COPPR);
        SubsCommissionConfig scgCOPPR = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_CAD);
        SubsCommissionConfig scgCAD = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_COAN);
        SubsCommissionConfig scgCOAN = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_COOV);
        SubsCommissionConfig scgCOOV = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_COPG);
        SubsCommissionConfig scgCOPG = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_COPS);
        SubsCommissionConfig scgCOPS = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        scgParams.put("type", SubsCommissionConfig.TYPE_COPQ);
        SubsCommissionConfig scgCOPQ = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);

        // init orderTotal and orderEarning
        double orderTotal = 0d;
        double orderEarning = 0d;

        // 2,3,4,5 scan Products to apply COPP or COPS or COPPR or CAD
        for (OrderLineCheckModel orderLine : orderLineCheckModels) {
            Double productCommission = MyNumberUtil.roundDouble(orderLine.getCommission(), 2);
            String productId = orderLine.getProductId();
            double productPrice = orderLine.getPrice();
            double productQuantity = orderLine.getQuantity().doubleValue();
            boolean hasCOPP = false;
            boolean hasCOPS = false;
            boolean hasCOPPR = false;

            // check COPP
            if (productCommission != null && productCommission > 0) {
                if (pca.getHasCommissionCopp()) {       // Subscriber can use COPP
                    orderEarning += productCommission * productPrice * productQuantity;
                    hasCOPP = true;
                }
            }

            // no have COPP, check COPS
            if (!hasCOPP && scgCOPS != null) {
                List<ProductSet> pss = findProductSets(scgCOPS, productId);
                for (ProductSet ps : pss) {
                    orderEarning += ps.getCommission() * productPrice * productQuantity;
                    hasCOPS = true;
                }
            }

            // neither have COPP nor COPS, check COPPR
            if (!hasCOPP && !hasCOPS && scgCOPPR != null) {
                ConditionCommissionConfig ccc = findConditionCommissionConfig(scgCOPPR, productPrice, null);
                if (ccc != null) {
                    orderEarning += ccc.getCommission() * productPrice * productQuantity;
                    hasCOPPR = true;
                }
            }

            // neither have COPP nor COPS nor COPPR, check CAD
            if (!hasCOPP && !hasCOPS && !hasCOPPR && scgCAD != null) {
                Double cadCommission = scgCAD.getCommission();
                if(cadCommission != null && cadCommission > 0) {
                    orderEarning += cadCommission * productPrice * productQuantity;
                }
            }

            orderTotal += (orderLine.getPrice() * orderLine.getQuantity());
        }
        dto.setOrderTotal(orderTotal);
        dto.setOrderEarning(orderEarning);

        // 6. process COAN for seller and its ancestors (TOWER) or its managers (LEVEL)
        if (scgCOAN == null) {  // orderEarning is not divided to seller's ancestors, it earns all orderEarning
            dto.setCbs(orderEarning);
        }
        else if (subscriber.getSubsConfig().getCoanType().equals(SubsConfig.TYPE_COAN_TOWER)) {
            // create CBS for seller at layer = 1
            int layer = 1;
            Double cbsCommission = getCommissionByLayer(scgCOAN.getLayerCommissionConfigs(), layer);
            if (cbsCommission != null && cbsCommission > 0) {
                double cbsEarning = orderEarning * cbsCommission;
                dto.setCbs(cbsEarning);
            }
        }
        else if (subscriber.getSubsConfig().getCoanType().equals(SubsConfig.TYPE_COAN_LEVEL)) {
            logger.error("LEVEL not yet supported");
            // support this case later
        }
        else {
            logger.error("Unsupported COAN type: " + subscriber.getSubsConfig().getCoanType());
        }

        // 7. find COOV
        if (scgCOOV != null) {     // COOV found
            ConditionCommissionConfig ccc = findConditionCommissionConfig(scgCOOV, orderTotal, null);
            if (ccc != null) {
                double coovEarning = ccc.getCommission() * orderTotal;
                dto.setCoov(coovEarning);
            }
        }

        // 8. find COPGs
        if (scgCOPG != null) {    // COPG found
            List<PriorityGroup> pgs = findPriorityGroups(scgCOPG, affiliateId);
            double copgEarning = 0d;
            for (PriorityGroup pg : pgs) {
                copgEarning += pg.getCommission() * orderTotal;
            }
            dto.setCopg(copgEarning);
        }

        // 9. apply DiscountCode if any
        if (discount != null) {          // CDC found
            double dcaValue = 0 - discount * orderTotal;
            dto.setCdc(dcaValue);
        }

        // 10. find COPQ
        double copqEarning = 0d;
        if (scgCOPQ != null) {     // COPQ found
            for (OrderLineCheckModel orderLine : orderLineCheckModels) {
                String productId = orderLine.getProductId();
                double productPrice = orderLine.getPrice();
                double productQuantity = orderLine.getQuantity().doubleValue();
                ConditionCommissionConfig ccc = findConditionCommissionConfig(scgCOPQ, productQuantity, productId);
                if (ccc != null) {
                    copqEarning += ccc.getCommission() * productPrice * productQuantity;
                }
            }
        }
        dto.setCopq(copqEarning);

        return dto;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void evaluateCOASV() throws BusinessException {
        logger.info("evaluateCOASV");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = MyDateUtil.convertToUTCDate(calendar.getTime());
        Date toDate = MyDateUtil.convertToUTCDate(new Date());

        Map<String, Object> scgParams = new HashMap<>();
        scgParams.put("isEnabled", Boolean.TRUE);
        List<String> subscriberIds = specificDao.getSubscriberIds();
        for (String subscriberId : subscriberIds) {
            scgParams.put("subscriberId", subscriberId);
            scgParams.put("type", SubsCommissionConfig.TYPE_COASV);
            SubsCommissionConfig scgCOASV = genericDao.readUnique(SubsCommissionConfig.class, scgParams, false);
            if (scgCOASV == null) {
                continue;
            }

            Subscriber subscriber = validationService.getSubscriber(subscriberId, false);
            List<String> affiliateIds = specificDao.getAffiliateIdsBySubscriberId(subscriberId);
            for(String affiliateId : affiliateIds) {
                Double salesValue = specificDao.getOrderRevenueByAffiliateIdAndSubscriberId(affiliateId, subscriberId,
                        fromDate, toDate);
                ConditionCommissionConfig ccc = findConditionCommissionConfig(scgCOASV, salesValue, null);
                if (ccc != null) {
                    Affiliate affiliate = validationService.getAffiliate(affiliateId, false);
                    double scgEarning = ccc.getCommission() * salesValue;
                    createCommission(Commission.TYPE_COASV, ccc.getCommission(), scgEarning,
                            affiliate, subscriber, null, scgCOASV, ccc, null, null);
                    increaseEarningBalance(affiliateId, subscriberId, scgEarning);
                }
            }
        }
    }

    @Override
    @Secured({SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public byte[] export(Date from, Date to) throws BusinessException {
        logger.info("export: " + " - from: " + from + " -> to: " + to);

        // create excel work book
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Commissions");

        // create headers
        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Loại");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Phần trăm");
        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Thu nhập");
        Cell cell4 = row.createCell(3);
        cell4.setCellValue("Cộng tác viên");
        Cell cell5 = row.createCell(4);
        cell5.setCellValue("Công ty");
        Cell cell6 = row.createCell(5);
        cell6.setCellValue("Đơn hàng");
        Cell cell7 = row.createCell(6);
        cell7.setCellValue("Mã giảm giá");

        // add Commissions page by page
        SearchCriteria<CommissionSearchCriteria> searchCriteria = new SearchCriteria<>();
        int pageSize = 1000;
        int startRow = 1;
        searchCriteria.setCriteria(new CommissionSearchCriteria());
        searchCriteria.setPageIndex(1);
        searchCriteria.setPageSize(pageSize);
        searchCriteria.setSortName("createdAt");

        // do authorization
        // SubsAdmin can export Commissions of its Subscriber
        // Affiliate can only export its Commissions
        CustomCriteria customCriteria = new CustomCriteria();
        customCriteria.setValue("createdAt>=", from);
        customCriteria.setValue("createdAt<=", to);

        // render first page
        searchCriteria.setCustomCriteria(customCriteria);
        SearchResult<CommissionDto> searchResult = this.search(searchCriteria); // this method does authorization
        logger.info("Export Page 1: " + searchResult.getList().size() + " items");
        this.renderCommissionsToExcelSheet(searchResult.getList(), sheet, startRow);
        // render the rest
        int numberOfPages = searchResult.getNumOfPages();
        for (int pageIndex = 2; pageIndex <= numberOfPages; pageIndex++) {
            searchCriteria.setPageIndex(pageIndex);
            searchResult = this.search(searchCriteria);
            logger.info("Export Page " + pageIndex + ": " + searchResult.getList().size() + " items");
            startRow += pageSize;
            this.renderCommissionsToExcelSheet(searchResult.getList(), sheet, startRow);
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

        return bos.toByteArray();
    }

    @Override
    @Secured({SystemRole.ROLE_AFFILIATE, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_ADMIN})
    public SearchResult<CommissionDto> search(SearchCriteria<CommissionSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Commission> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Commission criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Commission.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Commission());
        }

        // do authorization
          // SubsAdmin can only search Commissions of its Subscriber
          // Affiliate can only search its Commissions
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            if (searchCriteria.getCriteria().getSubscriberId() != null) {
                searchCriteria.getCriteria().setSubscriberId(null);
            }
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("subscriberId", loggedSubsAdmin.getSubscriberId());
        }
        else if (isAffiliate) {
            if (searchCriteria.getCriteria().getAffiliateId() != null) {
                searchCriteria.getCriteria().setAffiliateId(null);
            }
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("affiliateId", loggedUserDto.getId());
        }

        // do biz action
        SearchResult<Commission> searchResult;
        SearchResult<CommissionDto> dtoSearchResult;
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }


    // Utilities
    private void createCommission(String type, Double percentage, Double earning,
                                  Affiliate affiliate, Subscriber subscriber, String orderId,
                                  SubsCommissionConfig scg, ConditionCommissionConfig ccc,
                                  PriorityGroup pg, DiscountCodeApplied dca) {
        Commission commission = new Commission();
        commission.setType(type);
        commission.setValue(percentage);
        commission.setEarning(earning);
        commission.setAffiliate(affiliate);
        commission.setAffiliateId(affiliate.getId());   // for log
        commission.setSubscriber(subscriber);
        commission.setSubscriberId(subscriber.getId()); // for log
        commission.setOrderId(orderId);

        if (scg != null) {
            SubsCommissionConfigApplied scga = beanMapper.map(scg, SubsCommissionConfigApplied.class);

            // set commission, startDate, endDate, lowerCondition, upperCondition for COPPR, COOV, COASV, COPQ
            if (ccc != null) {
                beanMapper.map(ccc, scga);
            }
            // set commission, startDate, endDate for COPG
            else if (pg != null) {
                beanMapper.map(pg, scga);
            }
            // set commission for other types
            else {
                scga.setCommission(percentage);
                scga.setSubscriberId(subscriber.getId());
            }

            scga.setId(null);
            genericDao.create(scga);

            commission.setSubsCommissionConfigApplied(scga);
        }

        if (dca != null) {
            commission.setDiscountCodeApplied(dca);
        }

        logger.info("Commission created: " + commission);
        genericDao.create(commission);
    }

    private SearchResult<CommissionDto> createDtoSearchResult(SearchResult<Commission> searchResult) {
        SearchResult<CommissionDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<CommissionDto> dtos = new ArrayList<>();
        for (Commission pdo : searchResult.getList()) {
            CommissionDto dto = beanMapper.map(pdo, CommissionDto.class);
            Order order = genericDao.read(Order.class, dto.getOrderId(), false);
            dto.setOrderNumber(order.getNumber());
            dtos.add(dto);
        }
        result.setList(dtos);

        return result;
    }

    private ConditionCommissionConfig findConditionCommissionConfig(SubsCommissionConfig scg, double value, String productId) {
        if (value < 0 || scg == null) {
            return null;
        }

        for (ConditionCommissionConfig ccc : scg.getConditionCommissionConfigs()) {
            Double commission = ccc.getCommission();
            if (commission == null || commission.doubleValue() <= 0) {
                continue;
            }

            if (isCccEligible(value, ccc, productId)) {
                return ccc;
            }
        }

        return null;
    }

    private List<PriorityGroup> findPriorityGroups(SubsCommissionConfig scgCOPG, String sellerId) {
        List<PriorityGroup> result = new ArrayList<>();

        if (scgCOPG == null || sellerId == null) {
            return result;
        }

        for (PriorityGroup pg : scgCOPG.getPriorityGroups()) {
            Double commission = pg.getCommission();
            if (commission == null || commission.doubleValue() <= 0) {
                continue;
            }

            if (isPriorityGroupEligible(pg, sellerId)) {
                result.add(pg);
            }
        }

        return result;
    }

    private List<ProductSet> findProductSets(SubsCommissionConfig scgCOPS, String productId) {
        List<ProductSet> result = new ArrayList<>();

        if (scgCOPS == null || productId == null) {
            return result;
        }

        for (ProductSet ps : scgCOPS.getProductSets()) {
            Double commission = ps.getCommission();
            if (commission == null || commission <= 0) {
                continue;
            }

            if (isProductSetEligible(ps, productId)) {
                result.add(ps);
            }
        }

        return result;
    }

    private Double getCommissionByLayer(Set<LayerCommissionConfig> layerCommissionConfigs, int layer) {
        if (layerCommissionConfigs == null || layerCommissionConfigs.size() == 0) {
            return null;
        }

        for (LayerCommissionConfig lcg : layerCommissionConfigs) {
            if (lcg.getLayer().intValue() == layer) {
                return lcg.getCommission();
            }
        }

        return null;
    }

    private void increaseEarningBalance(String affiliateId, String subscriberId, double earning) {
        Map<String, Object> agentParams = new HashMap<>();
        agentParams.put("subscriberId", subscriberId);
        agentParams.put("affiliateId", affiliateId);
        Agent agent = genericDao.readUnique(Agent.class, agentParams, false);
        if (agent != null) {
            agent.setEarning(agent.getEarning() + earning);
        }
        genericDao.update(agent);
    }

    private boolean isCccEligible(double value, ConditionCommissionConfig ccc, String productId) {
        // validate date
        Date today = MyDateUtil.convertToUTCDate(new Date());
        boolean isDateEligible = validateDate(ccc.getStartDate(), today, ccc.getEndDate());

        // validate productId
        if(productId != null && !productId.equals(ccc.getProductId())) {
            return false;
        }

        // validate lower & upper conditions
        if (isDateEligible) {
            Integer lowerCondition = ccc.getLowerCondition();
            Integer upperCondition = ccc.getUpperCondition();
            if (lowerCondition != null && upperCondition != null) {
                if (lowerCondition.doubleValue() <= value && value <= upperCondition.doubleValue()) {
                    return true;
                }
            }
            else if (lowerCondition != null) {
                if (lowerCondition.doubleValue() <= value) {
                    return true;
                }
            }
            else if (upperCondition != null) {
                if (value <= upperCondition.doubleValue()) {
                    return true;
                }
            }
            else {
                return true;
            }
        }

        return false;
    }

    private boolean isPriorityGroupEligible (PriorityGroup pg, String sellerId) {
        // validate date
        Date today = MyDateUtil.convertToUTCDate(new Date());
        boolean isDateEligible = validateDate(pg.getStartDate(), today, pg.getEndDate());

        if (isDateEligible) {
            for (PriorityGroupAffiliate pgA : pg.getAffiliates()) {
                if (pgA.getAffiliateId().equals(sellerId)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isProductSetEligible(ProductSet ps, String productId) {
        // validate date
        Date today = MyDateUtil.convertToUTCDate(new Date());
        boolean isDateEligible = validateDate(ps.getStartDate(), today, ps.getEndDate());

        if (isDateEligible) {
            for (ProductSetProduct psP : ps.getProducts()) {
                if (psP.getProductId().equals(productId)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void renderCommissionsToExcelSheet(List<CommissionDto> commissionDtos, XSSFSheet sheet, int startRow) {
        for (CommissionDto commissionDto : commissionDtos) {
            Row row = sheet.createRow(startRow++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(commissionDto.getType());
            DecimalFormat df = new DecimalFormat("##.##");
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(df.format(commissionDto.getValue()));
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(commissionDto.getEarning());
            Cell cell4 = row.createCell(3);
            AffiliateDto affiliate = commissionDto.getAffiliate();
            String affiliateName = affiliate.getFirstName() + " " + affiliate.getLastName();
            cell4.setCellValue(affiliate.getNickname() + " - " + affiliateName);
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(commissionDto.getSubscriber().getCompanyName());
            Cell cell6 = row.createCell(5);
            cell6.setCellValue(commissionDto.getOrderNumber());
            if (commissionDto.getDiscountCodeApplied() != null) {
                Cell cell7 = row.createCell(6);
                cell7.setCellValue(commissionDto.getDiscountCodeApplied().getCode());
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
