package com.hoang.linkshortener.exception;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 8713639038875182018L;

    public static final int    USER_NOT_FOUND = 0;
    public static final int    EMAIL_EXISTS   = 1;
    public static final int    NAME_EXISTS    = 2;
    public static final String DOMAIN_INVALID = "Domain {0} is not a valid domain name";
    private final int code;

    public BusinessException (int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode () {
        return code;
    }
}
