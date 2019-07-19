package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.PerformerDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PerformerSearchCriteria;

public interface PerformerService {

    SearchResult<PerformerDto> search(SearchCriteria<PerformerSearchCriteria> vmSearchCriteria) throws BusinessException;

}
