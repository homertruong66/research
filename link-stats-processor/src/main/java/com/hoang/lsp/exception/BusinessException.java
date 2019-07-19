package com.hoang.lsp.exception;

public class BusinessException extends Exception {

    public static final int INVALID_EVENT = 1;
    public static final int DATA_EXCEPTION = 2;

    private static final long serialVersionUID = 1L;

    private int code = 0;

    private String message;

    public BusinessException (String message) {
        this.message = message;
    }
	
	public BusinessException(int code, String message) {
    	this.code = code;
    	this.message = message;    	
	}

    public int getCode () {
        return code;
    }

    public void setCode (int code) {
        this.code = code;
    }

    @Override
    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    @Override
	public String toString() {
		return "Error in application: [code=" + code + ", message=" + message + "]";
	}				
}
