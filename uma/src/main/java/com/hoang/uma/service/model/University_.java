package com.hoang.uma.service.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(University.class)
public abstract class University_ {

	public static volatile SingularAttribute<University, Date> createdAt;
	public static volatile SingularAttribute<University, Domain> country;
	public static volatile SingularAttribute<University, String> countryId;
	public static volatile SingularAttribute<University, String> name;
	public static volatile SingularAttribute<University, String> id;
	public static volatile ListAttribute<University, Department> departments;
	public static volatile SingularAttribute<University, Integer> version;
	public static volatile SingularAttribute<University, Date> updatedAt;

}

