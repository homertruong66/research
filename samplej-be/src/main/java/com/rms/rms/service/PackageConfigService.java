package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.PackageConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PackageConfigSearchCriteria;
import com.rms.rms.common.view_model.PackageConfigUpdateModel;

public interface PackageConfigService {

    PackageConfigDto get(String id) throws BusinessException;

    SearchResult<PackageConfigDto> search(SearchCriteria<PackageConfigSearchCriteria> vmSearchCriteria) throws BusinessException;

    PackageConfigDto update(String id, PackageConfigUpdateModel updateModel) throws BusinessException;
    
}
