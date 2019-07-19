package com.hoang.srj.exception;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 8713639038875182018L;
    private final int code;
    public static final String DOMAIN_INVALID = "Domain {0} is not a valid domain name";

    // User
    public static final int    USER_NOT_FOUND = 0;
    public static final String USER_NOT_FOUND_MESSAGE = "User doesn't exist!";
    public static final int    USER_EMAIL_EXISTS  = 1;
    public static final String USER_EMAIL_EXISTS_MESSAGE = "Email already exists!";
    public static final int    USER_ROLES_EMPTY   = 2;
    public static final String USER_ROLES_EMPTY_MESSAGE = "User must have at least one Role!";

    // University
    public static final int    UNIVERSITY_NOT_FOUND = 100;
    public static final String UNIVERSITY_NOT_FOUND_MESSAGE = "University doesn't exist!";

    public BusinessException (int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode () {
        return code;
    }
}
