package com.hoang.app.service;

import java.util.List;

//import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.domain.Role;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class RoleServiceImpl implements RoleService {

	private Logger logger = Logger.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;	
	
// 	@Autowired
//	private DataSource dataSource;
 	 	
	@SuppressWarnings("unchecked")
	public Role get(String name) {
		logger.debug("get('" + name + "')" );
		
		String countQuery = "from Role r " +
							"where r.name = '" + name + "' ";
        List<Role> results = hibernateTemplate.find(countQuery);
        if (results == null || results.size() == 0) {
            return null;
        }

        return results.get(0);
	}	
}	
