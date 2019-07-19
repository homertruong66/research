package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.CommissionDto;
import com.rms.rms.common.dto.CommissionExperienceDto;
import com.rms.rms.common.dto.OrderDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.CommissionCheckModel;
import com.rms.rms.common.view_model.CommissionSearchCriteria;

import java.util.Date;

/**
 * homertruong
 */

public interface CommissionService {

    OrderDto calculateCommissions(String orderId) throws BusinessException;

    double calculateEarningByAffiliateIdAndOrderId(String affiliateId, String orderId) throws BusinessException;

    CommissionExperienceDto checkCommissions(CommissionCheckModel checkModel) throws BusinessException;

    void evaluateCOASV() throws BusinessException;

    byte[] export(Date from, Date to) throws BusinessException;

    SearchResult<CommissionDto> search(SearchCriteria<CommissionSearchCriteria> vmSearchCriteria) throws BusinessException;

}
