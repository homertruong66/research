package com.hoang.uma.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * homertruong
 */
public class ResponseDto implements Serializable {

    private static final long serialVersionUID = 1398775962163354764L;

    @JsonProperty
    private int status = 1;

    @JsonProperty
    private Object data;

    @JsonProperty
    private int code = 200;

    @JsonProperty
    private String message = "Request has been processed successfully!";

    public ResponseDto() {}

    public ResponseDto(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


