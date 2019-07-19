package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.AdminDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AdminCreateModel;
import com.rms.rms.common.view_model.AdminSearchCriteria;
import com.rms.rms.common.view_model.AdminUpdateModel;
import com.rms.rms.service.EmailService;
import com.rms.rms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class AdminController {

    @Autowired
    private PersonService personService;

    @Autowired
    private EmailService emailService;

    public ResponseDto create(AdminCreateModel createModel) throws BusinessException {
        AdminDto dto = personService.createAdmin(createModel);

        // process notification email asynchronously
//        String name = dto.getLastName() + " " + dto.getFirstName();
//        Email email = MyEmailUtil.createNotificationEmail(createModel.getEmail(), name, createModel.getPassword());
//        emailService.process(email);

        return new ResponseDto(dto);
    }

    public void delete(String id) throws BusinessException {
        personService.deleteAdmin(id);
    }

    public ResponseDto get(String id) throws BusinessException {
        AdminDto dto = personService.getAdmin(id);
        return new ResponseDto(dto);
    }

    public ResponseDto search(SearchCriteria<AdminSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<AdminDto> dtoSearchResult = personService.searchAdmins(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update(String id, AdminUpdateModel updateModel) throws BusinessException {
        AdminDto dto = personService.updateAdmin(id, updateModel);
        return new ResponseDto(dto);
    }

}
