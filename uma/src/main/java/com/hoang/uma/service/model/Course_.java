package com.hoang.uma.service.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Course.class)
public abstract class Course_ {

	public static volatile SingularAttribute<Course, Date> createdAt;
	public static volatile ListAttribute<Course, Clazz> clazzes;
	public static volatile SingularAttribute<Course, Integer> numberOfCredits;
	public static volatile SingularAttribute<Course, String> departmentId;
	public static volatile SingularAttribute<Course, String> name;
	public static volatile SingularAttribute<Course, String> id;
	public static volatile SingularAttribute<Course, Department> department;
	public static volatile SingularAttribute<Course, Date> updatedAt;
	public static volatile SingularAttribute<Course, Integer> version;

}

