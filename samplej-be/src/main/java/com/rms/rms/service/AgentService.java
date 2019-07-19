package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.AgentDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AgentSearchCriteria;

public interface AgentService {

    SearchResult<AgentDto> search(SearchCriteria<AgentSearchCriteria> vmSearchCriteria) throws BusinessException;
    
}