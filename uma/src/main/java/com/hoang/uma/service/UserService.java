package com.hoang.uma.service;

import com.hoang.uma.common.dto.TeacherDto;
import com.hoang.uma.common.dto.UserDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.*;
import com.hoang.uma.common.dto.SearchResultDto;
import com.hoang.uma.common.dto.StudentDto;

import java.util.List;

/**
 * homertruong
 */

public interface UserService {

    String assignTeacher (String teacherId, List<String> departmentIds) throws BusinessException;

    String create (UserCreateModel createModel) throws BusinessException;

    String createStudent (String departmentId, StudentCreateModel createModel) throws BusinessException;

    String createTeacher (TeacherCreateModel createModel) throws BusinessException;

    List<String> createTeachers (List<TeacherCreateModel> createModels) throws BusinessException;

    void delete (String id) throws BusinessException;

    void deleteStudent (String studentId) throws BusinessException;

    void deleteTeacher (String teacherId) throws BusinessException;

    UserDto get (String id) throws BusinessException;

    List<UserDto> get (int fieldNum) throws BusinessException;

    String getIdByEmail (String email) throws BusinessException;

    StudentDto getStudent (String studentId) throws BusinessException;

    TeacherDto getTeacher (String teacherId) throws BusinessException;

    SearchResultDto<UserDto> search (UserSearchCriteria searchCriteria) throws BusinessException;

    SearchResultDto<StudentDto> searchStudents (StudentSearchCriteria searchCriteria) throws BusinessException;

    SearchResultDto<TeacherDto> searchTeachers (TeacherSearchCriteria searchCriteria) throws BusinessException;

    String update (String id, UserUpdateModel updateModel) throws BusinessException;

    String updateStudent (String studentId, StudentUpdateModel updateModel) throws BusinessException;

    String updateTeacher (String teacherId, TeacherUpdateModel updateModel) throws BusinessException;

}
