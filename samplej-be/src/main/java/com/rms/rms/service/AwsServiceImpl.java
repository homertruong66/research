package com.rms.rms.service;

import com.rms.rms.common.dto.AwsS3PreSignedUrlDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AwsS3PreSignedUrlModel;
import com.rms.rms.integration.AwsClient;
import com.rms.rms.integration.exception.AwsIntegrationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class AwsServiceImpl implements AwsService {

    private Logger logger = Logger.getLogger(AwsServiceImpl.class);

    @Autowired
    private AwsClient awsClient;

    @Override
    public AwsS3PreSignedUrlDto generateAwsS3PreSignedUrl(AwsS3PreSignedUrlModel model) throws BusinessException {
        logger.info("generateAwsS3PreSignedUrl: " + model);

        AwsS3PreSignedUrlDto result;
        try {
            result = awsClient.generateAwsS3PreSignedUrl(model.getBucketName(), model.getObjectKey());
        }
        catch (AwsIntegrationException aie) {
            logger.error("Error generating Aws S3 PreSigned Url: " + aie.getMessage(), aie);
            throw new BusinessException(BusinessException.AWS_S3_GENERATE_PRE_SIGNED_URL_FAILED,
                String.format(BusinessException.AWS_S3_GENERATE_PRE_SIGNED_URL_FAILED_MESSAGE, model.toString()));
        }

        return result;
    }
}
