package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.DiscountCodeDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.DiscountCodeCreateModel;
import com.rms.rms.common.view_model.DiscountCodeSearchCriteria;
import com.rms.rms.common.view_model.DiscountCodeUpdateModel;

/**
 * homertruong
 */

public interface DiscountCodeService {
    
    DiscountCodeDto create(DiscountCodeCreateModel createModel) throws BusinessException;

    DiscountCodeDto get(String id) throws BusinessException;

    SearchResult<DiscountCodeDto> search(SearchCriteria<DiscountCodeSearchCriteria> vmSearchCriteria) throws BusinessException;

    DiscountCodeDto update(String id, DiscountCodeUpdateModel updateModel) throws BusinessException;
    
}