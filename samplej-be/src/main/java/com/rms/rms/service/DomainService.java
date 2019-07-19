package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.DomainDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.DomainSearchCriteria;

/**
 * homertruong
 */

public interface DomainService {

    SearchResult<DomainDto> search(SearchCriteria<DomainSearchCriteria> vmSearchCriteria) throws BusinessException;

}
