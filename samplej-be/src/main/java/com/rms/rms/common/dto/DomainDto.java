package com.rms.rms.common.dto;

import java.io.Serializable;

/**
 * homertruong
 */

public class DomainDto implements Serializable {

	private static final long serialVersionUID = 6886866071113509579L;

	private String provinceId;
	private String code;
	private String id;
	private String name;
	private String status;
	private String type;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "DomainDto{" +
				"provinceId='" + provinceId + '\'' +
				", code='" + code + '\'' +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", status='" + status + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
