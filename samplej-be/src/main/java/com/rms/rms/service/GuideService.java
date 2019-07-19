package com.rms.rms.service;

import com.rms.rms.common.dto.GuideDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.GuideUpdateModel;

/**
 * homertruong
 */

public interface GuideService {
    
    GuideDto get(String target) throws BusinessException;

    GuideDto update(String id, GuideUpdateModel updateModel) throws BusinessException;
    
}