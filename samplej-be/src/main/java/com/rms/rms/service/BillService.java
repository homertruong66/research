package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.BillDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.BillSearchCriteria;

public interface BillService {

    BillDto confirm(String id) throws BusinessException;

    void generate() throws BusinessException;

    SearchResult<BillDto> search(SearchCriteria<BillSearchCriteria> vmSearchCriteria) throws BusinessException;
}