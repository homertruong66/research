package com.rms.rms.service;

import com.rms.rms.common.dto.SubsEmailConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsEmailConfigUpdateModel;

/**
 * homertruong
 */

public interface SubsEmailConfigService {

    SubsEmailConfigDto get(String id) throws BusinessException;

    SubsEmailConfigDto update(String id, SubsEmailConfigUpdateModel updateModel) throws BusinessException;
    
}