package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsCommissionConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsCommissionConfigSearchCriteria;
import com.rms.rms.common.view_model.SubsCommissionConfigUpdateModel;
import com.rms.rms.service.SubsCommissionConfigService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class SubsCommissionConfigController {

    @Autowired
    private SubsCommissionConfigService subsCommissionConfigService;

    public ResponseDto get(String id) throws BusinessException {
        SubsCommissionConfigDto dto = subsCommissionConfigService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto search(SearchCriteria<SubsCommissionConfigSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<SubsCommissionConfigDto> dtoSearchResult = subsCommissionConfigService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update(String id, SubsCommissionConfigUpdateModel updateModel) throws BusinessException {
        SubsCommissionConfigDto dto = subsCommissionConfigService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
