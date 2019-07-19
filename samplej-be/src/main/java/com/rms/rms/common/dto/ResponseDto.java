package com.rms.rms.common.dto;

import java.io.Serializable;

/**
 * homertruong
 */

public class ResponseDto implements Serializable {

    private static final long serialVersionUID = 1398775962163354764L;

    private int status = 1;
    private int code = 200;
    private String message = "Request has been processed successfully!";
    private Object data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ResponseDto() {
    }

    public ResponseDto(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "status=" + status +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}