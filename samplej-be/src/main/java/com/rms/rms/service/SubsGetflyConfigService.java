package com.rms.rms.service;

import com.rms.rms.common.dto.SubsGetflyConfigDto;
import com.rms.rms.common.dto.SubsGetflyConfigTestDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsGetflyConfigUpdateModel;

public interface SubsGetflyConfigService {

    SubsGetflyConfigDto get(String id) throws BusinessException;

    SubsGetflyConfigTestDto test(String id) throws BusinessException;
    
    SubsGetflyConfigDto update(String id, SubsGetflyConfigUpdateModel updateModel) throws BusinessException;
    
}
