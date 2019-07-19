package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsAlertConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsAlertConfigUpdateModel;
import com.rms.rms.service.SubsAlertConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * homertruong
 */
public class SubsAlertConfigController {

    @Autowired
    private SubsAlertConfigService subsAlertConfigService;

    public ResponseDto get(String id) throws BusinessException {
        SubsAlertConfigDto dto = subsAlertConfigService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto list() throws BusinessException {
        List<SubsAlertConfigDto> dtos = subsAlertConfigService.list();
        return new ResponseDto(dtos);
    }

    public ResponseDto update(String id, SubsAlertConfigUpdateModel updateModel) throws BusinessException {
        SubsAlertConfigDto dto = subsAlertConfigService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
