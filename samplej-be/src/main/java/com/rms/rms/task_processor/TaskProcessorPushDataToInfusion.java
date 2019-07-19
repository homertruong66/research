package com.rms.rms.task_processor;

import com.rms.rms.common.dto.OrderDto;
import com.rms.rms.common.dto.SubsInfusionConfigDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessorPushDataToInfusion {

    private Logger logger = Logger.getLogger(TaskProcessorPushDataToInfusion.class);

    @Autowired
    private OrderService orderService;

    public void process(OrderDto orderDto, SubsInfusionConfigDto subsInfusionConfigDto, UserDto loggedUserDto) {
        logger.info("process: " + orderDto + " - subsInfusionConfigDto: " + subsInfusionConfigDto + " - loggedUserDto: " + loggedUserDto);

        try {
            // put Authentication object to Spring Security Context
            Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // push data to Infusion
            if (subsInfusionConfigDto.getAccessToken() == null) {
                return;
            }
            orderService.sendDataToInfusion(orderDto);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
