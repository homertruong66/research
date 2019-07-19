package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ChannelDto;
import com.rms.rms.common.dto.ChannelViewDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.ChannelCreateModel;
import com.rms.rms.common.view_model.ChannelSearchCriteria;
import com.rms.rms.common.view_model.ChannelUpdateModel;
import com.rms.rms.service.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private PersonService personService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShareService shareService;

    public ResponseDto create(ChannelCreateModel createModel) throws BusinessException {
        ChannelDto dto = channelService.create(createModel);
        return new ResponseDto(dto);
    }

    public ResponseDto get(String id) throws BusinessException {
        ChannelDto dto = channelService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto search(SearchCriteria<ChannelSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<ChannelDto> dtoSearchResult = channelService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update(String id, ChannelUpdateModel updateModel) throws BusinessException {
        ChannelDto dto = channelService.update(id, updateModel);
        return new ResponseDto(dto);
    }

    public ResponseDto view(String id)  throws BusinessException {
        ChannelViewDto channelViewDto = new ChannelViewDto();
        channelViewDto.setClickCount(shareService.countClicksByChannelId(id));
        channelViewDto.setCustomerCount(customerService.countByChannelId(id));
        channelViewDto.setOrderCount(orderService.countByChannelId(id));
        channelViewDto.setClicksByDate(shareService.getClicksByDateByChannelId(id));

        // ChannelView Order revenue
        Double orderRevenue = orderService.getRevenueByChannelId(id, null);
        channelViewDto.setOrderRevenue(orderRevenue);

        // ChannelView Approved Order revenue = approvedOrderRevenue + commissionsDoneOrderRevenue
        Double approvedOrderRevenue = orderService.getApprovedRevenueByChannelId(id);
        Double commissionsDoneOrderRevenue = orderService.getCommissionsDoneRevenueByChannelId(id);
        channelViewDto.setApprovedOrderRevenue(approvedOrderRevenue + commissionsDoneOrderRevenue);

        // ChannelView NonApproved Order revenue = ChannelView Order revenue - ChannelView Approved Order revenue
        Double nonApprovedOrderRevenue = channelViewDto.getOrderRevenue() - channelViewDto.getApprovedOrderRevenue();
        channelViewDto.setNonApprovedOrderRevenue(nonApprovedOrderRevenue);

        return new ResponseDto(channelViewDto);
    }

}
