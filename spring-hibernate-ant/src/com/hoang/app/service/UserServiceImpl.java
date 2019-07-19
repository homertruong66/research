package com.hoang.app.service;

import java.util.List;

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

import com.hoang.app.domain.User;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.util.DbUtil;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class UserServiceImpl implements UserService {
	
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
		
	@Autowired
	private HibernateTemplate hibernateTemplate;	
	
// 	@Autowired
//	private DataSource dataSource;
 	 	
	@SuppressWarnings("unchecked")
	public User get(String username) {
		logger.debug("get('" + username + "')" );
		
		String selQuery = 	"from User u " +
							"where u.username = '" + username + "' ";
        List<User> results = hibernateTemplate.find(selQuery);
        if (results == null || results.size() == 0) {
            return null;
        }

        return results.get(0);
	}	
	
	@SuppressWarnings("unchecked")
	public User get(String username, String password) {
		logger.debug("get('" + username + "', '" + password + "')" );
		
		String selQuery = 	"from User u " +
							"where u.username = '" + username + "' " +
							"and u.password = '" + password + "' ";
        List<User> results = hibernateTemplate.find(selQuery);        											
        if (results == null || results.size() == 0) {
            return null;
        }

        return results.get(0);		 
	}
	 
	@SuppressWarnings("unchecked")
	public List<User> getByPerson(Long personId) {
		logger.debug("getByPerson(" + personId + ")" );
		
		String selQuery = 	"from User u " +
							"where u.party.id = " + personId;
		List<User> results = hibernateTemplate.find(selQuery);
		 
		return results;
	}
	 
	@SuppressWarnings("unchecked")
	public List<User> getByOrganization(Long organizationId) {
		logger.debug("getByOrganization(" + organizationId + ")" );
		
		String selQuery = 	"from User u " +
							"where u.party.id = " + organizationId;
		List<User> results = hibernateTemplate.find(selQuery);
		 
		return results;
	}	
	    
	// Search	 
	public PagedResultDTO<User> search(int pageSize, int pageIndex, String username) {
		 logger.debug("search('" + username + "')" );
		 
		 String countQuery = 	"select count(*) " +
				         		"from User u " +
				         		"where u.username like '%" + username + "%' ";

		 DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		 criteria.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
		 criteria.addOrder(Order.asc("username"));

		 return DbUtil.search(hibernateTemplate, pageSize, pageIndex, countQuery, null, criteria);
	}
	 
	// Other Services
    public boolean isPersonInUse(Long personId) {
    	 logger.debug("isPersonInUse(" + personId + ")" );
    	 
         List<User> users = getByPerson(personId);
         if (users == null || users.size() == 0) {
            return false;
         }

         return true;
    }

    public boolean isOrganizationInUse(Long organizationId) {
    	 logger.debug("isOrganizationInUse(" + organizationId + ")" );
    	 
         List<User> users = getByOrganization(organizationId);
         if (users == null || users.size() == 0) {
            return false;
         }

         return true;
    }

    public boolean isCurrentPasswordCorret(String username, String currentPassword) {
    	 logger.debug("isCurrentPasswordCorret('" + username + "', '" + currentPassword + "')" );
    	 
         User user = get(username, currentPassword);
         if (user == null) {
            return false;
         }

         return true;
    }
}	
