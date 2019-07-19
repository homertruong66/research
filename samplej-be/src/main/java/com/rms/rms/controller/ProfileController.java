package com.rms.rms.controller;

import com.rms.rms.common.dto.PersonDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PersonUpdateModel;
import com.rms.rms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfileController {

    @Autowired
    private PersonService personService;

    public ResponseDto get(String id) throws BusinessException {
        PersonDto dto = personService.getProfile(id);
        return new ResponseDto(dto);
    }

    public ResponseDto update(String id, PersonUpdateModel updateModel) throws BusinessException {
        PersonDto dto = personService.updateProfile(id, updateModel);
        return new ResponseDto(dto);
    }
}