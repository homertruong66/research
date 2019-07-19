package com.rms.rms.task_processor;

import com.rms.rms.common.dto.UserDto;
import com.rms.rms.service.CommissionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessorEvaluateCOASV {

    private Logger logger = Logger.getLogger(TaskProcessorEvaluateCOASV.class);

    @Autowired
    private CommissionService commissionService;

    public void process(UserDto loggedUserDto) {
        logger.info("process: " + loggedUserDto);

        // put Authentication object to Spring Security Context
        Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            commissionService.evaluateCOASV();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
