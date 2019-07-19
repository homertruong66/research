package com.rms.rms.service;

import com.rms.rms.common.dto.SubsRewardConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsRewardConfigUpdateModel;

public interface SubsRewardConfigService {

    SubsRewardConfigDto get(String id) throws BusinessException;

    SubsRewardConfigDto update(String id, SubsRewardConfigUpdateModel updateModel) throws BusinessException;


}
