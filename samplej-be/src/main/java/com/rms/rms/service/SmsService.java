package com.rms.rms.service;

import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SmsModel;

/**
 * homertruong
 */

public interface SmsService {
    
    String send(SmsModel smsModel) throws BusinessException;

}
