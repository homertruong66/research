package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SystemAlertDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SystemAlertUpdateModel;
import com.rms.rms.service.SystemAlertService;
import org.springframework.beans.factory.annotation.Autowired;

public class SystemAlertController {

    @Autowired
    private SystemAlertService systemAlertService;

    public ResponseDto get(String id) throws BusinessException {
        SystemAlertDto dto = systemAlertService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto update(String id, SystemAlertUpdateModel updateModel) throws BusinessException {
        SystemAlertDto dto = systemAlertService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
