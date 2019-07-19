package com.hoang.module.es.service;

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

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.util.DbUtil;
import com.hoang.module.es.domain.Place;

/**
*
* @author Hoang Truong
*/

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class PlaceServiceImpl implements PlaceService  {

	private Logger logger = Logger.getLogger(PlaceServiceImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
    // Get   
    @SuppressWarnings("unchecked")
	public Place get(String name) {       
    	logger.debug("get('" + name + "')" );
		
    	String selQuery = 	"from Place p " +
    						"where p.name = '" + name + "' ";
        List<Place> results = hibernateTemplate.find(selQuery);
        if (results == null || results.size() == 0) {
            return null;
        }

        return results.get(0);    
    }

    // Search
    public PagedResultDTO<Place> search(int pageSize, int pageIndex, String name) {
		 logger.debug("search('" + name + "')" );
		 
		 String countQuery = 	"select count(*) " +
				         		"from Place p " +
				         		"where p.name like '%" + name + "%' ";

		 DetachedCriteria criteria = DetachedCriteria.forClass(Place.class);
		 criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		 criteria.addOrder(Order.asc("name"));

		 return DbUtil.search(hibernateTemplate, pageSize, pageIndex, countQuery, null, criteria);         
    }
}
