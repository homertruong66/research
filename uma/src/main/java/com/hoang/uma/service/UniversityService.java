package com.hoang.uma.service;

import com.hoang.uma.common.dto.UniversityDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.*;
import com.hoang.uma.common.dto.CourseDto;
import com.hoang.uma.common.dto.DepartmentDto;
import com.hoang.uma.common.dto.SearchResultDto;

/**
 * homertruong
 */

public interface UniversityService {

    String create(UniversityCreateModel createModel) throws BusinessException;

    String createDepartment (String id, DepartmentCreateModel createModel) throws BusinessException;

    String createCourse (String departmentId, CourseCreateModel createModel) throws BusinessException;

    void delete(String id) throws BusinessException;

    void deleteDepartment (String departmentId) throws BusinessException;

    void deleteCourse (String courseId) throws BusinessException;

    UniversityDto get(String id) throws BusinessException;

    DepartmentDto getDepartment (String departmentId) throws BusinessException;

    CourseDto getCourse (String courseId) throws BusinessException;

    SearchResultDto<UniversityDto> search(UniversitySearchCriteria searchCriteria) throws BusinessException;

    SearchResultDto<DepartmentDto> searchDepartments (DepartmentSearchCriteria searchCriteria) throws BusinessException;

    SearchResultDto<CourseDto> searchCourses (CourseSearchCriteria searchCriteria) throws BusinessException;

    String update(String id, UniversityUpdateModel updateModel) throws BusinessException;

    String updateDepartment (String departmentId, DepartmentUpdateModel updateModel) throws BusinessException;

    String updateCourse (String courseId, CourseUpdateModel updateModel) throws BusinessException;

}
