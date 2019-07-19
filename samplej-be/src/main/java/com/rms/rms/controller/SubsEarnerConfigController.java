package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsEarnerConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsEarnerConfigUpdateModel;
import com.rms.rms.service.SubsEarnerConfigService;
import org.springframework.beans.factory.annotation.Autowired;

public class SubsEarnerConfigController {

    @Autowired
    private SubsEarnerConfigService subsEarnerConfigService;

    public ResponseDto get (String id) throws BusinessException {
        SubsEarnerConfigDto dto = subsEarnerConfigService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto update (String id, SubsEarnerConfigUpdateModel updateModel) throws BusinessException {
        SubsEarnerConfigDto dto = subsEarnerConfigService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
