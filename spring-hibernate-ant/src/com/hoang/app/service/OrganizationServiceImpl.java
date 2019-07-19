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

import com.hoang.app.domain.Organization;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.util.DbUtil;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class OrganizationServiceImpl implements OrganizationService {  
	
	private Logger logger = Logger.getLogger(OrganizationServiceImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;	
	
    // Search
    public PagedResultDTO<Organization> search(int pageSize, int pageIndex, String name) {
		 logger.debug("search('" + name + "')" );
		 
		 String countQuery = 	"select count(*) " +
				         		"from Organization o " +
				         		"where o.name like '%" + name + "%' ";

		 DetachedCriteria criteria = DetachedCriteria.forClass(Organization.class);
		 criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		 criteria.addOrder(Order.asc("name"));

		 return DbUtil.search(hibernateTemplate, pageSize, pageIndex, countQuery, null, criteria);   	
    }
}
