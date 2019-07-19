package com.hoang.uma.service.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Student.class)
public abstract class Student_ extends User_ {

	public static volatile SingularAttribute<Student, Integer> year;
	public static volatile SingularAttribute<Student, String> departmentId;
	public static volatile SingularAttribute<Student, Department> department;
	public static volatile ListAttribute<Student, ClazzStudent> clazzStudents;

}

