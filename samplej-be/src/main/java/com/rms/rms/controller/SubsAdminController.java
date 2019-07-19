package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsAdminDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsAdminCreateModel;
import com.rms.rms.common.view_model.SubsAdminSearchCriteria;
import com.rms.rms.common.view_model.SubsAdminUpdateModel;
import com.rms.rms.service.AuthenService;
import com.rms.rms.service.PersonService;
import com.rms.rms.task_processor.TaskProcessorCreateEmailAndNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * homertruong
 */
public class SubsAdminController {

    @Autowired
    private TaskProcessorCreateEmailAndNotification tpCreateEmailAndNotification;

    @Autowired
    private PersonService personService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    public ResponseDto addRole(String subsAdminId, String roleName) throws BusinessException {
        SubsAdminDto dto = personService.addRoleForSubsAdmin(subsAdminId,roleName);
        return new ResponseDto(dto);
    }

    public ResponseDto create(String subscriberId, SubsAdminCreateModel createModel) throws BusinessException, IOException {
        SubsAdminDto dto = personService.createSubsAdmin(subscriberId, createModel);

        UserDto loggedUserDto = authenService.getLoggedUser();

        taskExecutorService.submit(() -> {
            tpCreateEmailAndNotification.processOnSubsAdminCreated(dto, createModel, loggedUserDto);
        });

        return new ResponseDto(dto);
    }

    public void delete(String id) throws BusinessException {
        personService.deleteSubsAdmin(id);
    }

    public ResponseDto get(String id) throws BusinessException {
        SubsAdminDto dto = personService.getSubsAdmin(id);
        return new ResponseDto(dto);
    }

    public ResponseDto grantRoot(String id) throws BusinessException {
        SubsAdminDto dto = personService.grantRootForSubsAdmin(id);
        return new ResponseDto(dto);
    }

    public ResponseDto removeRole(String id, String roleName) throws BusinessException {
        SubsAdminDto dto = personService.removeRoleForSubsAdmin(id, roleName);
        return new ResponseDto(dto);
    }

    public ResponseDto revokeRoot(String id) throws BusinessException {
        SubsAdminDto dto = personService.revokeRootForSubsAdmin(id);
        return new ResponseDto(dto);
    }

    public ResponseDto search(SearchCriteria<SubsAdminSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<SubsAdminDto> dtoSearchResult = personService.searchSubsAdmins(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update(String id, SubsAdminUpdateModel updateModel) throws BusinessException {
        SubsAdminDto dto = personService.updateSubsAdmin(id, updateModel);
        return new ResponseDto(dto);
    }

}
