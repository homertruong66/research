package com.rms.rms.service;

import com.rms.rms.common.dto.AwsS3PreSignedUrlDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AwsS3PreSignedUrlModel;

/**
 * homertruong
 */

public interface AwsService {

    AwsS3PreSignedUrlDto generateAwsS3PreSignedUrl(AwsS3PreSignedUrlModel model) throws BusinessException;
    
}