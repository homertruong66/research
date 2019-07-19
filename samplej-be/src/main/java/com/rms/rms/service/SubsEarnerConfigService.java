package com.rms.rms.service;

import com.rms.rms.common.dto.SubsEarnerConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsEarnerConfigUpdateModel;

public interface SubsEarnerConfigService {

    SubsEarnerConfigDto get (String id) throws BusinessException;

    SubsEarnerConfigDto update (String id, SubsEarnerConfigUpdateModel updateModel) throws BusinessException;


}
