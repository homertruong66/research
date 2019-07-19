package com.hvn.spring.dto;

import java.util.ArrayList;
import java.util.List;


public class TeacherTransferListDTO {
	
	private List<UniversityDTO> universities = new ArrayList<UniversityDTO>();
	
	/**
	 * Left
	 */
	private List<DepartmentDTO> leftDepartments = new ArrayList<DepartmentDTO>();
	
	private List<TeacherDTO> leftTeachers = new ArrayList<TeacherDTO>();
	
	
	/**
	 * Right
	 */	
	private List<DepartmentDTO> rightDepartments = new ArrayList<DepartmentDTO>();
	
	private List<TeacherDTO> rightTeachers = new ArrayList<TeacherDTO>();

	
	public List<UniversityDTO> getUniversities() {
		return universities;
	}

	public void setUniversities(List<UniversityDTO> universities) {
		this.universities = universities;
	}

	public List<DepartmentDTO> getLeftDepartments() {
		return leftDepartments;
	}

	public void setLeftDepartments(List<DepartmentDTO> leftDepartments) {
		this.leftDepartments = leftDepartments;
	}

	public List<TeacherDTO> getLeftTeachers() {
		return leftTeachers;
	}

	public void setLeftTeachers(List<TeacherDTO> leftTeachers) {
		this.leftTeachers = leftTeachers;
	}

	public List<DepartmentDTO> getRightDepartments() {
		return rightDepartments;
	}

	public void setRightDepartments(List<DepartmentDTO> rightDepartments) {
		this.rightDepartments = rightDepartments;
	}

	public List<TeacherDTO> getRightTeachers() {
		return rightTeachers;
	}

	public void setRightTeachers(List<TeacherDTO> rightTeachers) {
		this.rightTeachers = rightTeachers;
	}
}

