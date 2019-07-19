package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsConfigUpdateModel;
import com.rms.rms.service.SubsConfigService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class SubsConfigController {

    @Autowired
    private SubsConfigService subsConfigService;

    public ResponseDto get(String id) throws BusinessException {
        SubsConfigDto dto = subsConfigService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto getTermsAndConditions(String id) throws BusinessException {
        String dto = subsConfigService.getTermsAndConditions(id);
        return new ResponseDto(dto);
    }

    public ResponseDto update(String id, SubsConfigUpdateModel updateModel) throws BusinessException {
        SubsConfigDto dto = subsConfigService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
