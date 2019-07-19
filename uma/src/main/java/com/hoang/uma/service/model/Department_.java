package com.hoang.uma.service.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ {

	public static volatile SingularAttribute<Department, Date> createdAt;
	public static volatile SingularAttribute<Department, String> universityId;
	public static volatile ListAttribute<Department, Course> courses;
	public static volatile SingularAttribute<Department, University> university;
	public static volatile SingularAttribute<Department, String> name;
	public static volatile ListAttribute<Department, Student> students;
	public static volatile SingularAttribute<Department, String> id;
	public static volatile ListAttribute<Department, DepartmentTeacher> departmentTeachers;
	public static volatile SingularAttribute<Department, Integer> version;
	public static volatile SingularAttribute<Department, Date> updatedAt;

}

