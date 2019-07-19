package com.hvn.spring.dto;

import java.util.ArrayList;
import java.util.List;

import com.hvn.spring.model.Teacher;

public class TeacherListDTO {
	
	private List<TeacherDTO> teachers = new ArrayList<TeacherDTO>();
	
	private long totalResult;
	
	public void copyFrom(List<Teacher> fromTeachers) {
		
		this.teachers.clear();
		
		if (fromTeachers != null) {
			fromTeachers.stream().forEach(teacher -> {
				TeacherDTO teacherDTO = new TeacherDTO();
				teacherDTO.copyFrom(teacher);
				
				this.teachers.add(teacherDTO);
			});
		}
	}

	public void copyTo(final List<Teacher> teachers) {

		teachers.clear();
		
		this.teachers.stream().forEach(teacherDTO -> {
			Teacher teacher = new Teacher();
			teacherDTO.copyTo(teacher);
			
			teachers.add(teacher);
		});
	}

	public List<TeacherDTO> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeacherDTO> teachers) {
		this.teachers = teachers;
	}

	public long getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(long totalResult) {
		this.totalResult = totalResult;
	}
}

