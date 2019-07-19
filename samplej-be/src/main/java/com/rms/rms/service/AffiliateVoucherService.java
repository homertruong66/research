package com.rms.rms.service;

import com.rms.rms.common.dto.AffiliateVoucherDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AffiliateVoucherCreateModel;

import java.util.List;

public interface AffiliateVoucherService {

    List<AffiliateVoucherDto> create(AffiliateVoucherCreateModel createModel) throws BusinessException;
    
}