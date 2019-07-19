package com.rms.rms.task_processor;

import com.rms.rms.common.dto.AffiliateDto;
import com.rms.rms.common.dto.OrderDto;
import com.rms.rms.common.dto.SubsGetResponseConfigDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.service.OrderService;
import com.rms.rms.service.PersonService;
import com.rms.rms.service.SubsGetResponseConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessorPushDataToGetResponse {

    private Logger logger = Logger.getLogger(TaskProcessorPushDataToGetResponse.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private PersonService personService;

    @Autowired
    private SubsGetResponseConfigService subsGetResponseConfigService;

//    public void createCustomFields(SubsGetResponseConfigDto subsGetResponseConfigDto, UserDto loggedUserDto) {
//        logger.info("createCustomFields: " + subsGetResponseConfigDto + " - loggedUserDto: " + loggedUserDto);
//
//        try {
//            // put Authentication object to Spring Security Context
//            Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // push data to GetResponse
//            if (subsGetResponseConfigDto.getApiKey() == null || subsGetResponseConfigDto.getCampaignDefaultId() == null) {
//                return;
//            }
//            subsGetResponseConfigService.createCustomFields();
//        }
//        catch (Exception ex) {
//            logger.error(ex.getMessage(), ex);
//        }
//    }

    public void getCustomFieldIdByName(String name, SubsGetResponseConfigDto subsGetResponseConfigDto, UserDto loggedUserDto) {
        logger.info("getCustomFieldIdByName: " + name + " - subsGetResponseConfigDto: " + subsGetResponseConfigDto + " - loggedUserDto: " + loggedUserDto);

        try {
            // put Authentication object to Spring Security Context
            Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // push data to GetResponse
            if (subsGetResponseConfigDto.getApiKey() == null || subsGetResponseConfigDto.getCampaignDefaultId() == null) {
                return;
            }
            subsGetResponseConfigService.getCustomFieldIdByName(name);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void process(AffiliateDto affiliateDto, SubsGetResponseConfigDto subsGetResponseConfigDto, UserDto loggedUserDto) {
        logger.info("process: " + affiliateDto + " - subsGetResponseConfigDto: " + subsGetResponseConfigDto + " - loggedUserDto: " + loggedUserDto);

        try {
            // put Authentication object to Spring Security Context
            Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // push data to GetResponse
            if (subsGetResponseConfigDto.getApiKey() == null || subsGetResponseConfigDto.getCampaignDefaultId() == null) {
                return;
            }
            personService.sendDataToGetResponse(affiliateDto);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void process(OrderDto orderDto, SubsGetResponseConfigDto subsGetResponseConfigDto, UserDto loggedUserDto) {
        logger.info("process: " + orderDto + " - subsGetResponseConfigDto: " + subsGetResponseConfigDto + " - loggedUserDto: " + loggedUserDto);

        try {
            // put Authentication object to Spring Security Context
            Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // push data to GetResponse
            if (subsGetResponseConfigDto.getApiKey() == null || subsGetResponseConfigDto.getCampaignDefaultId() == null) {
                return;
            }
            orderService.sendDataToGetResponse(orderDto);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
