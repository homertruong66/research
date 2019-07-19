package com.hvn.spring.dto;

import java.util.ArrayList;
import java.util.List;

public class DepartmentListDTO {

	private long totalResult;
	
	private List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();

	public long getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(long totalResult) {
		this.totalResult = totalResult;
	}

	public List<DepartmentDTO> getDepartments() {
		return departments;
	}

	public void setDepartments(List<DepartmentDTO> departments) {
		this.departments = departments;
	}
}
