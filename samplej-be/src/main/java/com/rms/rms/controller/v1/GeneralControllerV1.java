package com.rms.rms.controller.v1;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AwsS3PreSignedUrlModel;
import com.rms.rms.common.view_model.ChangePasswordModel;
import com.rms.rms.common.view_model.ResetPasswordModel;
import com.rms.rms.common.view_model.SmsModel;
import com.rms.rms.controller.GeneralController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1")
public class GeneralControllerV1 extends GeneralController {

    @Override
    @PostMapping(value = "/change_password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto changePassword(@RequestBody ChangePasswordModel model) throws BusinessException {
        return super.changePassword(model);
    }

    @PostMapping(value = "/aws-s3-pre-signed-url")
    public ResponseDto generateAwsS3PreSignedUrl(@RequestBody AwsS3PreSignedUrlModel model) throws BusinessException {
        return super.generateAwsS3PreSignedUrl(model);
    }

    @GetMapping(value = "/health")
    public void healthCheck() {
        super.healthCheck();
    }

    @Override
    @PostMapping(value = "/reset_password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void resetPassword(@RequestBody ResetPasswordModel model) throws BusinessException, IOException{
        super.resetPassword(model);
    }

    @PostMapping(value = "/sms", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto sendSms(@RequestBody @Valid SmsModel smsModel) throws BusinessException {
        return super.sendSms(smsModel);
    }

    @PostMapping(value = "/dashboard")
    public ResponseDto viewDashboard() throws BusinessException {
        return super.viewDashboard();
    }

}
