package com.hvn.spring.service;

import java.util.List;

import com.hvn.spring.dto.StudentDTO;
import com.hvn.spring.dto.TeacherDTO;
import com.hvn.spring.dto.TeacherTransferEditDTO;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.utils.criteria.SearchCriteria;

public interface PersonService {
	
	public <T> T delete(Class<?> clazz, long id);
	
	public ReturnValue<TeacherDTO> getTeacher(long id);
	
	public ReturnValue<StudentDTO> getStudent(long id);

	public ReturnValue<List<TeacherDTO>> getTeachersByDepartment(long departmentId);

	public <T> T transferTeachers(TeacherTransferEditDTO teacherTransferEditDTO);

	public ReturnValue<Long> saveTeacher(TeacherDTO dto);

	public ReturnValue<Long> saveStudent(StudentDTO dto);

	public ReturnValue<List<TeacherDTO>> searchTeachers(SearchCriteria searchCriteria);

	public ReturnValue<List<StudentDTO>> searchStudents(SearchCriteria searchCriteria);
}
