package com.rms.rms.task_processor;

import com.rms.rms.common.dto.OrderDto;
import com.rms.rms.common.dto.SubsGetflyConfigDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessorPushDataToGetfly {

    private Logger logger = Logger.getLogger(TaskProcessorPushDataToGetfly.class);

    @Autowired
    private OrderService orderService;

    public void process(OrderDto orderDto, SubsGetflyConfigDto subsGetflyConfigDto, UserDto loggedUserDto) {
        logger.info("process: " + orderDto + " - subsGetflyConfigDto: " + subsGetflyConfigDto + " - loggedUserDto: " + loggedUserDto);

        try {
            // put Authentication object to Spring Security Context
            Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // push data to Getfly
            if (subsGetflyConfigDto.getApiKey() == null || subsGetflyConfigDto.getBaseUrl() == null) {
                return;
            }
            orderService.sendDataToGetfly(orderDto);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
