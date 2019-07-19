package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.UploadErrorDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.UploadErrorSearchCriteria;
import com.rms.rms.service.UploadErrorService;
import org.springframework.beans.factory.annotation.Autowired;

public class UploadErrorController {

    @Autowired
    private UploadErrorService errorService;

    public ResponseDto search (SearchCriteria<UploadErrorSearchCriteria> searchCriteria) throws BusinessException {
        SearchResult<UploadErrorDto> dtoSearchResult = errorService.search(searchCriteria);
        return new ResponseDto(dtoSearchResult);
    }
}
