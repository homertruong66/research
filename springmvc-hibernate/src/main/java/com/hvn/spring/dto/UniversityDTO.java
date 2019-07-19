package com.hvn.spring.dto;

import com.hvn.spring.model.University;

public class UniversityDTO {

	private Integer version;
	private Long id;
	private String name;
	private String address;
	private String provine;
	private String country;
	private String phone;
	
	public void copyFrom(University university){
		this.setVersion(university.getVersion());
		this.setId(university.getId());
		this.setName(university.getName());
		this.setAddress(university.getAddress());
		this.setProvine(university.getProvince());
		this.setCountry(university.getCountry());
		this.setPhone(university.getPhone());
	}
	
	public void copyTo(University university){
		university.setVersion(this.version);
		university.setName(this.name);
		university.setAddress(this.address);
		university.setCountry(this.country);
		university.setProvince(this.provine);
		university.setPhone(this.phone);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvine() {
		return provine;
	}

	public void setProvine(String provine) {
		this.provine = provine;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
