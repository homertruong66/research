package com.rms.rms.service;

import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SmsModel;
import com.rms.rms.integration.SmsClient;
import com.rms.rms.integration.exception.SmsIntegrationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

/**
 * homertruong
 */

@Component
public class SmsServiceImpl implements SmsService {

    private Logger logger = Logger.getLogger(SmsServiceImpl.class);

    @Autowired
    private SmsClient smsClient;

    @Override
    @Secured({SystemRole.ROLE_AFFILIATE})
    public String send(SmsModel smsModel) throws BusinessException {
        logger.debug("sendSms: " + smsModel.toString());

        String smsId;
        try {
            smsId = smsClient.send(smsModel.getPhone(), smsModel.getMessage());
        }
        catch (SmsIntegrationException sie) {
            logger.error("send failed: " + sie.getMessage(), sie);
            return null;
        }

        return smsId;
    }

}
