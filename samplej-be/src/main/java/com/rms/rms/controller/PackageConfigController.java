package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.PackageConfigDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PackageConfigSearchCriteria;
import com.rms.rms.common.view_model.PackageConfigUpdateModel;
import com.rms.rms.service.PackageConfigService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class PackageConfigController {
    
    @Autowired
    private PackageConfigService packageConfigService;

    public ResponseDto get(String id) throws BusinessException {
        PackageConfigDto dto = packageConfigService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto search(SearchCriteria<PackageConfigSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<PackageConfigDto> dtoSearchResult = packageConfigService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update(String id, PackageConfigUpdateModel updateModel) throws BusinessException {
        PackageConfigDto dto = packageConfigService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
