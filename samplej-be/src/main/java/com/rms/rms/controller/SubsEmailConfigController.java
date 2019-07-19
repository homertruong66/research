package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsEmailConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsEmailConfigUpdateModel;
import com.rms.rms.service.SubsEmailConfigService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class SubsEmailConfigController {

    @Autowired
    private SubsEmailConfigService subsEmailConfigService;

    public ResponseDto get(String id) throws BusinessException {
        SubsEmailConfigDto dto = subsEmailConfigService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto update(String id, SubsEmailConfigUpdateModel updateModel) throws BusinessException {
        SubsEmailConfigDto dto = subsEmailConfigService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
