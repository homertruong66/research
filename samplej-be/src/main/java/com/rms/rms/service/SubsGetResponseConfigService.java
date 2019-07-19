package com.rms.rms.service;

import com.rms.rms.common.dto.SubsGetResponseConfigDto;
import com.rms.rms.common.dto.SubsGetResponseConfigTestDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsGetResponseConfigUpdateModel;

public interface SubsGetResponseConfigService {

//    void createCustomFields() throws BusinessException;

    SubsGetResponseConfigDto get(String id) throws BusinessException;

    void getCustomFieldIdByName(String name) throws BusinessException;

    SubsGetResponseConfigTestDto test(String id) throws BusinessException;
    
    SubsGetResponseConfigDto update(String id, SubsGetResponseConfigUpdateModel updateModel) throws BusinessException;
    
}
