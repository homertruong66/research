package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsPerformerConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsPerformerConfigUpdateModel;
import com.rms.rms.service.SubsPerformerConfigService;
import org.springframework.beans.factory.annotation.Autowired;

public class SubsPerformerConfigController {

    @Autowired
    private SubsPerformerConfigService subsPerformerConfigService;

    public ResponseDto get(String id) throws BusinessException {
        SubsPerformerConfigDto dto = subsPerformerConfigService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto update(String id, SubsPerformerConfigUpdateModel updateModel) throws BusinessException {
        SubsPerformerConfigDto dto = subsPerformerConfigService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
