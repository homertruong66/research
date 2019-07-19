package com.rms.rms.controller;

import com.rms.rms.common.dto.AffiliateVoucherDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AffiliateVoucherCreateModel;
import com.rms.rms.service.AffiliateVoucherService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AffiliateVoucherController {

    @Autowired
    private AffiliateVoucherService affiliateVoucherService;

    public ResponseDto create(AffiliateVoucherCreateModel createModel) throws BusinessException {
        List<AffiliateVoucherDto> dto = affiliateVoucherService.create(createModel);
        return new ResponseDto(dto);
    }
}
