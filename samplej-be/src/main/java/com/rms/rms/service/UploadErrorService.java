package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.UploadErrorDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.UploadErrorSearchCriteria;
import com.rms.rms.service.model.UploadError;

public interface UploadErrorService {

    UploadError create (UploadError uploadError) throws BusinessException;

    SearchResult<UploadErrorDto> search (SearchCriteria<UploadErrorSearchCriteria> vmSearchCriteria) throws BusinessException;

}
