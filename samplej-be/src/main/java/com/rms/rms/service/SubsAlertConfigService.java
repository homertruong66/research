package com.rms.rms.service;

import com.rms.rms.common.dto.SubsAlertConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsAlertConfigUpdateModel;

import java.util.List;

public interface SubsAlertConfigService {

    SubsAlertConfigDto get(String id) throws BusinessException;

    List<SubsAlertConfigDto> list() throws BusinessException;
    
    SubsAlertConfigDto update(String id, SubsAlertConfigUpdateModel updateModel) throws BusinessException;
    
}
