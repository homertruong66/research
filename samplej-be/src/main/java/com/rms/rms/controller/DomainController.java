package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.DomainDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.DomainSearchCriteria;
import com.rms.rms.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */

public class DomainController {

    @Autowired
    private DomainService domainService;

    public ResponseDto search(SearchCriteria<DomainSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<DomainDto> dtoSearchResult = domainService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }
}
