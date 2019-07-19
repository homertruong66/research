package com.hoang.app.service;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.domain.Person;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.util.DbUtil;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class PersonServiceImpl implements PersonService {    

	private Logger logger = Logger.getLogger(PersonServiceImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;	
	
    // Search
    public PagedResultDTO<Person> search(int pageSize, int pageIndex, String firstName) {
		 logger.debug("search('" + firstName + "')" );
		 
		 String countQuery = 	"select count(*) " +
				         		"from Person p " +
				         		"where p.firstName like '%" + firstName + "%' ";

		 DetachedCriteria criteria = DetachedCriteria.forClass(Person.class);
		 criteria.add(Restrictions.like("firstName", firstName, MatchMode.ANYWHERE));
		 criteria.addOrder(Order.asc("firstName"));

		 return DbUtil.search(hibernateTemplate, pageSize, pageIndex, countQuery, null, criteria); 
    }
}
