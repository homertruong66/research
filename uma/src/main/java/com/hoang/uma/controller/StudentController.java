package com.hoang.uma.controller;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.StudentCreateModel;
import com.hoang.uma.common.view_model.StudentSearchCriteria;
import com.hoang.uma.common.view_model.StudentUpdateModel;
import com.hoang.uma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

/**
 * homertruong
 */

public class StudentController {

    @Autowired
    protected UserService userService;

    public ResponseDto create (String departmentId,
                               @Valid StudentCreateModel createModel) throws BusinessException
    {
        return new ResponseDto(userService.createStudent(departmentId, createModel));
    }

    public void delete (String id) throws BusinessException {
        userService.deleteStudent(id);
    }

    public ResponseDto get (String id) throws BusinessException {
        return new ResponseDto(userService.getStudent(id));
    }

    public ResponseDto search (StudentSearchCriteria searchCriteria) throws BusinessException {
        return new ResponseDto(userService.searchStudents(searchCriteria));
    }

    public ResponseDto update (String studentId, @Valid StudentUpdateModel updateModel) throws BusinessException {
        return new ResponseDto(userService.updateStudent(studentId, updateModel));
    }
}
