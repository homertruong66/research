package com.rms.rms.service;

import com.rms.rms.common.dto.SystemAlertDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SystemAlertUpdateModel;

public interface SystemAlertService {

    SystemAlertDto get(String id) throws BusinessException;

    SystemAlertDto update(String id, SystemAlertUpdateModel updateModel) throws BusinessException;
}
