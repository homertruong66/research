package com.hoang.uma.persistence;

/**
 * homertruong
 */

public interface UniversityDao {

    boolean isCourseNameExistent (String courseName, String departmentId);

    boolean isDepartmentNameExistent (String departmentName, String id);

}
