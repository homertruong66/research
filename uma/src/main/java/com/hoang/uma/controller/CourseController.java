package com.hoang.uma.controller;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.CourseCreateModel;
import com.hoang.uma.common.view_model.CourseSearchCriteria;
import com.hoang.uma.common.view_model.CourseUpdateModel;
import com.hoang.uma.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

/**
 * homertruong
 */

public class CourseController {

    @Autowired
    protected UniversityService universityService;

    public ResponseDto create (String departmentId,
                               @Valid CourseCreateModel createModel) throws BusinessException
    {
        return new ResponseDto(universityService.createCourse(departmentId, createModel));
    }

    public void delete (String id) throws BusinessException {
        universityService.deleteCourse(id);
    }

    public ResponseDto get (String id) throws BusinessException {
        return new ResponseDto(universityService.getCourse(id));
    }

    public ResponseDto search (CourseSearchCriteria searchCriteria) throws BusinessException {
        return new ResponseDto(universityService.searchCourses(searchCriteria));
    }

    public ResponseDto update (String courseId, @Valid CourseUpdateModel updateModel) throws BusinessException {
        return new ResponseDto(universityService.updateCourse(courseId, updateModel));
    }
}
