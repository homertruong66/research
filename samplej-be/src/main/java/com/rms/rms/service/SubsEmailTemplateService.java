package com.rms.rms.service;

import com.rms.rms.common.dto.SubsEmailTemplateDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsEmailTemplateUpdateModel;

/**
 * homertruong
 */

public interface SubsEmailTemplateService {

    SubsEmailTemplateDto get (String id) throws BusinessException;

    SubsEmailTemplateDto getBySubscriberIdAndType(String type, String subscriberId) throws BusinessException;

    SubsEmailTemplateDto getByType (String type) throws BusinessException;

    SubsEmailTemplateDto update (String id, SubsEmailTemplateUpdateModel updateModel) throws BusinessException;

}