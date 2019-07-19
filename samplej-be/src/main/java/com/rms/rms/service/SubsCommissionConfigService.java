package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.SubsCommissionConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsCommissionConfigSearchCriteria;
import com.rms.rms.common.view_model.SubsCommissionConfigUpdateModel;

/**
 * homertruong
 */

public interface SubsCommissionConfigService {
    
    SubsCommissionConfigDto get(String id) throws BusinessException;

    SearchResult<SubsCommissionConfigDto> search(SearchCriteria<SubsCommissionConfigSearchCriteria> vmSearchCriteria) throws BusinessException;

    SubsCommissionConfigDto update(String id, SubsCommissionConfigUpdateModel updateModel) throws BusinessException;
    
}