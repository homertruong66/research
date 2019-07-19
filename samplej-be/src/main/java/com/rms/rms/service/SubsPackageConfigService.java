package com.rms.rms.service;

import com.rms.rms.common.dto.SubsPackageConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsPackageConfigUpdateModel;

public interface SubsPackageConfigService {

    SubsPackageConfigDto get(String id) throws BusinessException;
    
    SubsPackageConfigDto update(String id, SubsPackageConfigUpdateModel updateModel) throws BusinessException;
    
}
