package com.rms.rms.controller;

import com.rms.rms.common.dto.GuideDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.GuideUpdateModel;
import com.rms.rms.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class GuideController {

    @Autowired
    private GuideService guideService;

    public ResponseDto get(String target) throws BusinessException {
        GuideDto dto = guideService.get(target);
        return new ResponseDto(dto);
    }

    public ResponseDto update(String id, GuideUpdateModel updateModel) throws BusinessException {
        GuideDto dto = guideService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
