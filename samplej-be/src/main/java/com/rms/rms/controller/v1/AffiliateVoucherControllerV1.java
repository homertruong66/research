package com.rms.rms.controller.v1;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AffiliateVoucherCreateModel;
import com.rms.rms.controller.AffiliateVoucherController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/affiliate_vouchers")
public class AffiliateVoucherControllerV1 extends AffiliateVoucherController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody AffiliateVoucherCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

}