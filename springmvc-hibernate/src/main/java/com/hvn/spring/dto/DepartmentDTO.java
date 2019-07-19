package com.hvn.spring.dto;

import com.hvn.spring.model.Department;

public class DepartmentDTO {

	private long id;
	private String name;
	private String location;
	
	public void copyFrom(Department department) {
		this.id = department.getId();
		this.name = department.getName();
		this.location = department.getLocation();
	}
	
	public void copyTo(Department department) {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
