package com.hoang.uma.service.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Teacher.class)
public abstract class Teacher_ extends User_ {

	public static volatile ListAttribute<Teacher, Clazz> clazzes;
	public static volatile SingularAttribute<Teacher, String> degree;
	public static volatile SingularAttribute<Teacher, String> departmentId;
	public static volatile ListAttribute<Teacher, DepartmentTeacher> departmentTeachers;

}

