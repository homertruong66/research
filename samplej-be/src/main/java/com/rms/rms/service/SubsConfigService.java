package com.rms.rms.service;

import com.rms.rms.common.dto.SubsConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsConfigUpdateModel;

/**
 * homertruong
 */

public interface SubsConfigService {

    SubsConfigDto get(String id) throws BusinessException;

    String getTermsAndConditions(String id) throws BusinessException;

    SubsConfigDto update(String id, SubsConfigUpdateModel updateModel) throws BusinessException;
    
}