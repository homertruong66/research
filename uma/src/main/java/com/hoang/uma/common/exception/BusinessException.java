package com.hoang.uma.common.exception;

/**
 * homertruong
 */

public class BusinessException extends Exception {

    private static final long serialVersionUID = 8713639038875182018L;
    private final int code;

    // Common
    public static final int    USERNAME_OR_PASSOWRD_INVALID = 0;
    public static final String USERNAME_OR_PASSOWRD_INVALID_MESSAGE = "Username or Password is invalid";
    public static final int    DOMAIN_INVALID = 1;
    public static final String DOMAIN_INVALID_MESSAGE = "Domain '%s' is not a valid domain name";
    public static final int    COUNTRY_NOT_FOUND = 2;
    public static final String COUNTRY_NOT_FOUND_MESSAGE = "Country '%s' not found";

    // User
    public static final int    USER_NOT_FOUND = 50;
    public static final String USER_NOT_FOUND_MESSAGE = "User '%s' doesn't exist!";
    public static final int    USER_EMAIL_EXISTS  = 51;
    public static final String USER_EMAIL_EXISTS_MESSAGE = "Email '%s' already exists!";
    public static final int    USER_ROLES_EMPTY   = 52;
    public static final String USER_ROLES_EMPTY_MESSAGE = "User must have at least one Role!";
    public static final int    USER_NO_ROLES_FOUND   = 53;
    public static final String USER_NO_ROLES_FOUND_MESSAGE = "No Roles found!";

    // University
    public static final int    UNIVERSITY_NOT_FOUND = 111;
    public static final String UNIVERSITY_NOT_FOUND_MESSAGE = "University '%s' doesn't exist!";
    public static final int    UNIVERSITY_NAME_EXISTS  = 112;
    public static final String UNIVERSITY_NAME_EXISTS_MESSAGE = "University name '%s' already exists!";

    // Department
    public static final int    DEPARTMENT_NOT_FOUND = 222;
    public static final String DEPARTMENT_NOT_FOUND_MESSAGE = "Department '%s' doesn't exist!";
    public static final int    DEPARTMENT_NAME_EXISTS  = 223;
    public static final String DEPARTMENT_NAME_EXISTS_MESSAGE = "Department name '%s' already exists in this University!";

    // Course
    public static final int    COURSE_NOT_FOUND = 333;
    public static final String COURSE_NOT_FOUND_MESSAGE = "Course '%s' doesn't exist!";
    public static final int    COURSE_NAME_EXISTS  = 334;
    public static final String COURSE_NAME_EXISTS_MESSAGE = "Course name '%s' already exists in this Department!";
    public static final int    COURSE_CREDITS_INVALID  = 335;
    public static final String COURSE_CREDITS_INVALID_MESSAGE = "Course credits must between 1 and 3!";

    // Teacher
    public static final int    TEACHER_NOT_FOUND = 444;
    public static final String TEACHER_NOT_FOUND_MESSAGE = "Teacher '%s' doesn't exist!";
    public static final int    TEACHER_AGE_INVALID  = 445;
    public static final String TEACHER_AGE_INVALID_MESSAGE = "Teacher age must less than 100 !";
    public static final int    TEACHER_ASSIGN_DEPARTMENT_REQUIRED  = 446;
    public static final String TEACHER_ASSIGN_DEPARTMENT_REQUIRED_MESSAGE = "Department(s) for assignment must be specified!";
    public static final int    TEACHER_ALREADY_ASSIGNED  = 447;
    public static final String TEACHER_ALREADY_ASSIGNED_MESSAGE = "Teacher '%s' already assigned!";
    public static final int    TEACHER_UNASSIGN_DEPARTMENT_REQUIRED  = 448;
    public static final String TEACHER_UNASSIGN_DEPARTMENT_REQUIRED_MESSAGE = "Department(s) for unassignment must be specified!";
    public static final int    TEACHER_NOT_YET_ASSIGNED  = 449;
    public static final String TEACHER_NOT_YET_ASSIGNED_MESSAGE = "Teacher '%s' not yet assigned to this Department!";

    // Student
    public static final int    STUDENT_NOT_FOUND = 555;
    public static final String STUDENT_NOT_FOUND_MESSAGE = "Student '%s' doesn't exist!";
    public static final int    STUDENT_AGE_INVALID  = 557;
    public static final String STUDENT_AGE_INVALID_MESSAGE = "Student age must less than 100 !";
    public static final int    STUDENT_YEAR_INVALID  = 558;
    public static final String STUDENT_YEAR_INVALID_MESSAGE = "Student year must less than 5 !";
    public static final int    STUDENT_ALREADY_REGISTERED  = 559;
    public static final String STUDENT_ALREADY_REGISTERED_MESSAGE = "Student '%s' already registered this Class!";
    public static final int    STUDENT_NOT_YET_REGISTERED = 560;
    public static final String STUDENT_NOT_YET_REGISTERED_MESSAGE = "Student '%s' not yet registered this Class!";

    // Clazz
    public static final int    CLAZZ_NOT_FOUND = 666;
    public static final String CLAZZ_NOT_FOUND_MESSAGE = "Clazz '%s' doesn't exist!";
    public static final int    CLAZZ_DURATION_INVALID = 667;
    public static final String CLAZZ_DURATION_INVALID_MESSAGE = "Clazz duration must less than 4!";
    public static final int    CLAZZ_ALREADY_STARTED = 668;
    public static final String CLAZZ_ALREADY_STARTED_MESSAGE = "Clazz '%s' already STARTED!";
    public static final int    CLAZZ_START_ENDED = 669;
    public static final String CLAZZ_START_ENDED_MESSAGE = "Can not start the ENDED Clazz '%s'!";
    public static final int    CLAZZ_START_CANCELLED = 670;
    public static final String CLAZZ_START_CANCELLED_MESSAGE = "Can not start the CANCELLED Clazz '%s'!";
    public static final int    CLAZZ_ALREADY_ENDED = 671;
    public static final String CLAZZ_ALREADY_ENDED_MESSAGE = "Clazz '%s' already ENDED!";
    public static final int    CLAZZ_END_NEW = 672;
    public static final String CLAZZ_END_NEW_MESSAGE = "Can not end the NEW Clazz '%s'!";
    public static final int    CLAZZ_END_CANCELLED = 673;
    public static final String CLAZZ_END_CANCELLED_MESSAGE = "Can not end the CANCELLED Clazz '%s'!";
    public static final int    CLAZZ_ALREADY_CANCELLED = 674;
    public static final String CLAZZ_ALREADY_CANCELLED_MESSAGE = "Clazz '%s' already cancelled!";
    public static final int    CLAZZ_CANCEL_ENDED = 675;
    public static final String CLAZZ_CANCEL_ENDED_MESSAGE = "Can not cancel the ENDED Clazz '%s'!";

    public BusinessException (int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode () {
        return code;
    }
}
