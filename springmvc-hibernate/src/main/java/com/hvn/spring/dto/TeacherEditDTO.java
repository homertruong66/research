package com.hvn.spring.dto;

import java.util.List;

import com.hvn.spring.model.Teacher;

public class TeacherEditDTO {

	private TeacherDTO teacher;
	private List<DepartmentDTO> departments;

	public void copyFrom(Teacher teacher) {
		this.teacher = new TeacherDTO();
		this.teacher.setId(teacher.getId());
		this.teacher.setDegree(teacher.getDegree());
		
		if (teacher.getDepartment() != null) {
			DepartmentDTO departmentDTO = new DepartmentDTO();
			departmentDTO.copyFrom(teacher.getDepartment());
			
			this.teacher.setDepartment(departmentDTO);
		}
		
		this.teacher.setName(teacher.getName());
		this.teacher.setEmail(teacher.getEmail());
		this.teacher.setPhone(teacher.getPhone());
	}

	public void copyTo(Teacher teacher) {
		if (this.teacher != null) {
			teacher.setId(this.teacher.getId());
			teacher.setDegree(this.teacher.getDegree());
			teacher.setEmail(this.teacher.getEmail());
			teacher.setName(this.teacher.getName());
			teacher.setPhone(this.teacher.getPhone());
		}
	}

	public TeacherDTO getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
	}

	public List<DepartmentDTO> getDepartments() {
		return departments;
	}

	public void setDepartments(List<DepartmentDTO> departments) {
		this.departments = departments;
	}
}
