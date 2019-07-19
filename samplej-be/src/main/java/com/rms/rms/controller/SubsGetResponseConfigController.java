package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsGetResponseConfigDto;
import com.rms.rms.common.dto.SubsGetResponseConfigTestDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsGetResponseConfigUpdateModel;
import com.rms.rms.service.AuthenService;
import com.rms.rms.service.SubsGetResponseConfigService;
import com.rms.rms.task_processor.TaskProcessorPushDataToGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.ExecutorService;

public class SubsGetResponseConfigController {

    @Autowired
    private AuthenService authenService;
    
    @Autowired
    private SubsGetResponseConfigService subsGetResponseConfigService;

    @Autowired
    private TaskProcessorPushDataToGetResponse tpPushDataToGetResponse;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    public ResponseDto get(String id) throws BusinessException {
        SubsGetResponseConfigDto dto = subsGetResponseConfigService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto test(String id) throws BusinessException {
        SubsGetResponseConfigTestDto dto = subsGetResponseConfigService.test(id);
//        if (dto.isSuccess()) {
//            UserDto loggedUserDto = authenService.getLoggedUser();
//            SubsGetResponseConfigDto subsGetResponseConfigDto = subsGetResponseConfigService.get(id);
//            Runnable task = () -> tpPushDataToGetResponse.createCustomFields(subsGetResponseConfigDto, loggedUserDto);
//            taskExecutorService.submit(task);
//        }
        if (dto.isSuccess()) {
            UserDto loggedUserDto = authenService.getLoggedUser();
            SubsGetResponseConfigDto subsGetResponseConfigDto = subsGetResponseConfigService.get(id);
            Runnable task = () -> tpPushDataToGetResponse.getCustomFieldIdByName("phone", subsGetResponseConfigDto, loggedUserDto);
            taskExecutorService.submit(task);
        }
        return new ResponseDto(dto);
    }
    
    public ResponseDto update(String id, SubsGetResponseConfigUpdateModel updateModel) throws BusinessException {
        SubsGetResponseConfigDto dto = subsGetResponseConfigService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
