package com.hoang.uma.service.model;

import com.hoang.uma.common.enu.ClazzStatus;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Clazz.class)
public abstract class Clazz_ {

	public static volatile SingularAttribute<Clazz, Date> startedAt;
	public static volatile SingularAttribute<Clazz, Integer> version;
	public static volatile SingularAttribute<Clazz, String> room;
	public static volatile SingularAttribute<Clazz, Integer> duration;
	public static volatile SingularAttribute<Clazz, Date> createdAt;
	public static volatile SingularAttribute<Clazz, Teacher> teacher;
	public static volatile SingularAttribute<Clazz, String> teacherId;
	public static volatile SingularAttribute<Clazz, Date> endedAt;
	public static volatile SingularAttribute<Clazz, Course> course;
	public static volatile SingularAttribute<Clazz, String> id;
	public static volatile SingularAttribute<Clazz, String> courseId;
	public static volatile ListAttribute<Clazz, ClazzStudent> clazzStudents;
	public static volatile SingularAttribute<Clazz, ClazzStatus> status;
	public static volatile SingularAttribute<Clazz, Date> updatedAt;

}

