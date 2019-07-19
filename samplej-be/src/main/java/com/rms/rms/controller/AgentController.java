package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.AgentDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AgentSearchCriteria;
import com.rms.rms.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;

public class AgentController {

    @Autowired
    private AgentService agentService;

    public ResponseDto search(SearchCriteria<AgentSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<AgentDto> dtoSearchResult = agentService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

}
