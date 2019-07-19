package com.rms.rms.service;

import com.rms.rms.common.dto.PackageConfigAppliedDto;
import com.rms.rms.common.dto.SubscriberDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubscriberExtendModel;

public interface PackageConfigAppliedService {

    SubscriberDto extend(String subscriberId, SubscriberExtendModel extendModel) throws BusinessException;

    PackageConfigAppliedDto get(String id) throws BusinessException;
    
}
