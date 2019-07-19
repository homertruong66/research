package com.rms.rms.service;

import com.rms.rms.common.dto.SubsInfusionConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsInfusionConfigAuthorizeModel;

import java.io.IOException;

public interface SubsInfusionConfigService {

    String URL_KEY_TEMPLATE = "URL_%s";

    SubsInfusionConfigDto get(String id) throws BusinessException;

    String getAccessToken(String id) throws BusinessException, IOException;

    String getAuthorizeUrl(SubsInfusionConfigAuthorizeModel authorizeModel) throws BusinessException, IOException;

    String requestAccessToken(String id, String code) throws BusinessException, IOException;
    
}
