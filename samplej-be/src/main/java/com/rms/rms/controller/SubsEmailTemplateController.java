package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsEmailTemplateDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsEmailTemplateUpdateModel;
import com.rms.rms.service.SubsEmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class SubsEmailTemplateController {

    @Autowired
    private SubsEmailTemplateService subsEmailTemplateService;

    public ResponseDto get(String id) throws BusinessException {
        SubsEmailTemplateDto dto = subsEmailTemplateService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto update(String id, SubsEmailTemplateUpdateModel updateModel) throws BusinessException {
        SubsEmailTemplateDto dto = subsEmailTemplateService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
