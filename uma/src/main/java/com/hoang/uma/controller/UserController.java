package com.hoang.uma.controller;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.UserCreateModel;
import com.hoang.uma.common.view_model.UserSearchCriteria;
import com.hoang.uma.common.view_model.UserUpdateModel;
import com.hoang.uma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

/**
 * homertruong
 */

public class UserController {

    @Autowired
    protected UserService userService;

    public ResponseDto create (@Valid UserCreateModel createModel) throws BusinessException {
        return new ResponseDto(userService.create(createModel));
    }

    public void delete (String id) throws BusinessException {
        userService.delete(id);
    }

    public ResponseDto get (int fieldNum) throws BusinessException {
        return new ResponseDto(userService.get(fieldNum));
    }

    public ResponseDto get (String id) throws BusinessException {
        return new ResponseDto(userService.get(id));
    }

    public ResponseDto search (UserSearchCriteria searchCriteria) throws BusinessException {
        return new ResponseDto(userService.search(searchCriteria));
    }

    public ResponseDto update (String id, @Valid UserUpdateModel updateModel) throws BusinessException {
        return new ResponseDto(userService.update(id, updateModel));
    }
}
