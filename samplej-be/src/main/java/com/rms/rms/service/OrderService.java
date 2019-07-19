package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.OrderDto;
import com.rms.rms.common.dto.OrderViewDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.OrderCreateModel;
import com.rms.rms.common.view_model.OrderSearchCriteria;
import com.rms.rms.common.view_model.OrderUpdateModel;

import java.io.IOException;
import java.util.Date;

/**
 * homertruong
 */

public interface OrderService {

    OrderDto approve(String id) throws BusinessException;

    OrderDto confirmCommissionsGeneratedDone(String id) throws BusinessException;

    Long count() throws BusinessException;

    Long countByAffiliateId(String affiliateId) throws BusinessException;

    Long countByChannelId(String channelId) throws BusinessException;

    Long countBySubscriberId(String subscriberId) throws BusinessException;

    OrderDto create(OrderCreateModel createModel) throws BusinessException;

    byte[] export(Date from, Date to) throws BusinessException;

    OrderDto get(String id) throws BusinessException;

    Double getApprovedRevenue() throws BusinessException;

    Double getApprovedRevenueByChannelId(String channelId) throws BusinessException;

    Double getApprovedRevenueBySubscriberId(String subscriberId) throws BusinessException;

    Double getCommissionsDoneRevenue() throws BusinessException;

    Double getCommissionsDoneRevenueByChannelId(String channelId) throws BusinessException;

    Double getCommissionsDoneRevenueBySubscriberId(String subscriberId) throws BusinessException;

    Double getNewRevenue() throws BusinessException;

    Double getNewRevenueByChannelId(String channelId) throws BusinessException;

    Double getNewRevenueBySubscriberId(String subscriberId) throws BusinessException;

    Double getRejectedRevenue() throws BusinessException;

    Double getRejectedRevenueByChannelId(String channelId) throws BusinessException;

    Double getRejectedRevenueBySubscriberId(String subscriberId) throws BusinessException;

    Double getRevenue(String status) throws BusinessException;

    Double getRevenueByAffiliateId(String affiliateId, String status) throws BusinessException;

    Double getRevenueByChannelId(String channelId, String status) throws BusinessException;

    Double getRevenueBySubscriberId(String subscriberId, String status) throws BusinessException;

    OrderDto reject(String id, String reason) throws BusinessException;

    SearchResult<OrderDto> search(SearchCriteria<OrderSearchCriteria> vmSearchCriteria) throws BusinessException;

    OrderDto sendDataToGetfly(OrderDto orderDto) throws BusinessException, IOException;

    OrderDto sendDataToGetResponse(OrderDto orderDto) throws BusinessException, IOException;

    OrderDto sendDataToInfusion(OrderDto orderDto) throws BusinessException, IOException;

    OrderDto update(String id, OrderUpdateModel updateModel) throws BusinessException;

    void updateGetflySuccess(String id, boolean success);

    void updateGetResponseSuccess(String id, boolean success);

    void updateInfusionSuccess(String id, boolean success);

    OrderViewDto view(String id) throws BusinessException;

}