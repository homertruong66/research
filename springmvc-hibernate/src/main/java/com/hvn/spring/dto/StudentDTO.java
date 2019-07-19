package com.hvn.spring.dto;

import com.hvn.spring.model.Student;

public class StudentDTO {

	private long id;
	private String email;
	private String level;
	private String name;
	private String phone;
	
	public void copyFrom(Student student) {
		this.id = student.getId();
		this.email = student.getEmail();
		this.level = student.getLevel();
		this.name = student.getName();
		this.phone = student.getPhone();
	}
	
	public void copyTo(Student student) {
		student.setId(this.id);
		student.setEmail(this.email);
		student.setLevel(this.level);
		student.setName(this.name);
		student.setPhone(this.phone);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
