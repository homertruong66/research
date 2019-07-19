package com.hoang.srj.service;

import com.hoang.srj.view_model.UserUpdateModel;
import com.hoang.srj.dto.UserDto;
import com.hoang.srj.exception.BusinessException;
import com.hoang.srj.view_model.UserCreateModel;
import com.hoang.srj.view_model.UserSearchCriteria;

import java.util.List;

public interface UserService {

    UserDto create (UserCreateModel createModel) throws BusinessException;

    void delete (String id) throws BusinessException;

    UserDto get (String id) throws BusinessException;

    List<UserDto> search (UserSearchCriteria searchCriteria) throws BusinessException;

    UserDto update (UserUpdateModel updateModel) throws BusinessException;

}
