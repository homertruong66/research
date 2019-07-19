package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsGetflyConfigDto;
import com.rms.rms.common.dto.SubsGetflyConfigTestDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsGetflyConfigUpdateModel;
import com.rms.rms.service.SubsGetflyConfigService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class SubsGetflyConfigController {
    
    @Autowired
    private SubsGetflyConfigService subsGetflyConfigService;

    public ResponseDto get(String id) throws BusinessException {
        SubsGetflyConfigDto dto = subsGetflyConfigService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto test(String id) throws BusinessException {
        SubsGetflyConfigTestDto dto = subsGetflyConfigService.test(id);
        return new ResponseDto(dto);
    }
    
    public ResponseDto update(String id, SubsGetflyConfigUpdateModel updateModel) throws BusinessException {
        SubsGetflyConfigDto dto = subsGetflyConfigService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
