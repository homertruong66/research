package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.CustomerDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.CustomerCreateModel;
import com.rms.rms.common.view_model.CustomerSearchCriteria;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * homertruong
 */

public interface CustomerService {

    Long count() throws BusinessException;

    Long countByAffiliateId(String affiliateId) throws BusinessException;

    Long countByChannelId(String channelId) throws BusinessException;

    Long countBySubscriberId(String subscriberId) throws BusinessException;

    CustomerDto create(CustomerCreateModel customerCreateModel) throws BusinessException;

    byte[] export(Date from, Date to) throws BusinessException;

    SearchResult<CustomerDto> search(SearchCriteria<CustomerSearchCriteria> vmSearchCriteria) throws BusinessException;

    void upload(MultipartFile file)  throws BusinessException;

}