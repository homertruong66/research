package com.hvn.spring.dto;

import java.util.ArrayList;
import java.util.List;

import com.hvn.spring.model.Student;

public class StudentListDTO {
	
	private List<StudentDTO> students = new ArrayList<StudentDTO>();
	
	private long totalResult;
	
	public void copyFrom(List<Student> models) {
		
		this.students.clear();
		
		if (models != null) {
			models.stream().forEach(model -> {
				StudentDTO dto = new StudentDTO();
				dto.copyFrom(model);
				
				this.students.add(dto);
			});
		}
	}

	public void copyTo(final List<Student> models) {

		models.clear();
		
		this.students.stream().forEach(dto -> {
			Student model = new Student();
			dto.copyTo(model);
			
			models.add(model);
		});
	}

	public List<StudentDTO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentDTO> students) {
		this.students = students;
	}

	public long getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(long totalResult) {
		this.totalResult = totalResult;
	}
}

