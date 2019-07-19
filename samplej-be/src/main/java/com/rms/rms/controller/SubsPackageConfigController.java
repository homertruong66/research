package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsPackageConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsPackageConfigUpdateModel;
import com.rms.rms.service.SubsPackageConfigService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class SubsPackageConfigController {
    
    @Autowired
    private SubsPackageConfigService subsPackageConfigService;

    public ResponseDto get(String id) throws BusinessException {
        SubsPackageConfigDto dto = subsPackageConfigService.get(id);
        return new ResponseDto(dto);
    }
    
    public ResponseDto update(String id, SubsPackageConfigUpdateModel updateModel) throws BusinessException {
        SubsPackageConfigDto dto = subsPackageConfigService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
