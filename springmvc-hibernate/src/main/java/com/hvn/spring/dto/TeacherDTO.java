package com.hvn.spring.dto;

import com.hvn.spring.model.Teacher;

public class TeacherDTO {

	private long id;
	private String degree;
	private DepartmentDTO department;
	private String name;
	private String email;
	private String phone;

	public void copyFrom(Teacher teacher) {
		this.id = teacher.getId();
		this.degree = teacher.getDegree();
		
		if (teacher.getDepartment() != null) {
			DepartmentDTO departmentDTO = new DepartmentDTO();
			departmentDTO.copyFrom(teacher.getDepartment());
			
			this.department = departmentDTO;
		}
		
		this.name = teacher.getName();
		this.email = teacher.getEmail();
		this.phone = teacher.getPhone();
	}

	public void copyTo(Teacher teacher) {
		teacher.setId(this.id);
		teacher.setDegree(this.getDegree());
		teacher.setEmail(this.email);
		teacher.setName(this.name);
		teacher.setPhone(this.phone);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public DepartmentDTO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDTO department) {
		this.department = department;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
