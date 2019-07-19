package com.rms.rms.service;

import com.rms.rms.common.dto.SubsPerformerConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsPerformerConfigUpdateModel;

public interface SubsPerformerConfigService {

    SubsPerformerConfigDto get(String id) throws BusinessException;

    SubsPerformerConfigDto update(String id, SubsPerformerConfigUpdateModel updateModel) throws BusinessException;


}
