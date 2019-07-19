package com.hvn.spring.dto;

public class DomainDTO {
	private long id;
	private String code;
	private String name;

	public DomainDTO() {
	}

	public DomainDTO(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
