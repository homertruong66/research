package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsRewardConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsRewardConfigUpdateModel;
import com.rms.rms.service.SubsRewardConfigService;
import org.springframework.beans.factory.annotation.Autowired;

public class SubsRewardConfigController {

    @Autowired
    private SubsRewardConfigService subsRewardConfigService;

    public ResponseDto get (String id) throws BusinessException {
        SubsRewardConfigDto dto = subsRewardConfigService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto update (String id, SubsRewardConfigUpdateModel updateModel) throws BusinessException {
        SubsRewardConfigDto dto = subsRewardConfigService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
