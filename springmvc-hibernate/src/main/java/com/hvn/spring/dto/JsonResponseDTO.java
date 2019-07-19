package com.hvn.spring.dto;

import com.hvn.spring.utils.constants.URLMappingConstants;

public class JsonResponseDTO {
	private String status;
	private String error;
	private Object data;
	
	public JsonResponseDTO() {
		this.status = URLMappingConstants.RESPONSE_STATUS_SUCCESS;
		this.error = "";
	}
	
	public JsonResponseDTO(Object data) {
		this.status = URLMappingConstants.RESPONSE_STATUS_SUCCESS;
		this.error = "";
		this.data = data;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
