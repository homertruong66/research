package com.rms.rms.task_processor;

import com.rms.rms.common.dto.AwsS3UrlDto;
import com.rms.rms.common.dto.PersonDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.util.MyEmailUtil;
import com.rms.rms.integration.AwsClient;
import com.rms.rms.integration.exception.AwsIntegrationException;
import com.rms.rms.service.EmailService;
import com.rms.rms.service.model.Email;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TaskProcessorHandleBigReport {

    private static Logger logger = Logger.getLogger(TaskProcessorHandleBigReport.class);

    @Autowired
    private AwsClient awsClient;

    @Autowired
    private EmailService emailService;

    public void process(byte[] data, String fileName, PersonDto personDto, String type) {
        logger.info("process: " + fileName + ", " + personDto + ", " + type);

        AwsS3UrlDto s3UrlDto = null;
        try {
            s3UrlDto = awsClient.upload(data, fileName, personDto.getUser().getEmail());

            Email email = MyEmailUtil.createEmailFromEmailTemplate(
                String.format("Export %s ", type),
                "Bấm vào đây để tải dữ liệu về: " + s3UrlDto.getUrl(),
                personDto.getUser().getEmail(),
                String.join(" ", personDto.getFirstName(), personDto.getLastName())
            );
            emailService.send(email);
        }
        catch (AwsIntegrationException aie) {
            logger.error("process failed: " + aie.getMessage(), aie);
        }
        catch (BusinessException be) {
            logger.error("error sending email: " + be.getMessage(), be);
        }
    }
}
