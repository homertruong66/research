package com.hoang.uma.controller;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.DepartmentCreateModel;
import com.hoang.uma.common.view_model.DepartmentSearchCriteria;
import com.hoang.uma.common.view_model.DepartmentUpdateModel;
import com.hoang.uma.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

/**
 * homertruong
 */

public class DepartmentController {

    @Autowired
    protected UniversityService universityService;

    public ResponseDto create (String universityId,
                               @Valid DepartmentCreateModel createModel) throws BusinessException
    {
        return new ResponseDto(universityService.createDepartment(universityId, createModel));
    }

    public void delete (String id) throws BusinessException {
        universityService.deleteDepartment(id);
    }

    public ResponseDto get (String id) throws BusinessException {
        return new ResponseDto(universityService.getDepartment(id));
    }

    public ResponseDto search (DepartmentSearchCriteria searchCriteria) throws BusinessException {
        return new ResponseDto(universityService.searchDepartments(searchCriteria));
    }

    public ResponseDto update (String departmentId, @Valid DepartmentUpdateModel updateModel) throws BusinessException {
        return new ResponseDto(universityService.updateDepartment(departmentId, updateModel));
    }
}
