package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.PerformerDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PerformerSearchCriteria;
import com.rms.rms.service.PerformerService;
import org.springframework.beans.factory.annotation.Autowired;


public class PerformerController {

    @Autowired
    private PerformerService performerService;

    public ResponseDto search (SearchCriteria<PerformerSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<PerformerDto> dtoSearchResult = performerService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

}
