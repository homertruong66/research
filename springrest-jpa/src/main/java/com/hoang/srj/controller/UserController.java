package com.hoang.srj.controller;

import com.hoang.srj.service.UserService;
import com.hoang.srj.view_model.UserCreateModel;
import com.hoang.srj.view_model.UserUpdateModel;
import com.hoang.srj.dto.UserDto;
import com.hoang.srj.exception.BusinessException;
import com.hoang.srj.view_model.UserSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;

public class UserController {

    @Autowired
    protected UserService userService;

    public UserDto create (@Valid UserCreateModel createModel) throws BusinessException {
        return userService.create(createModel);
    }

    public void delete (String id) throws BusinessException {
        userService.delete(id);
    }

    public UserDto get (String id) throws BusinessException {
        return userService.get(id);
    }

    public List<UserDto> search (UserSearchCriteria searchCriteria) throws BusinessException {
        return userService.search(searchCriteria);
    }

    public UserDto update (@Valid UserUpdateModel updateModel) throws BusinessException {
        return userService.update(updateModel);
    }
}
