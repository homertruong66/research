package com.hoang.module.es.service;

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
import com.hoang.module.es.domain.Event;

/**
*
* @author Hoang Truong
*/

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class EventServiceImpl implements EventService  {
	
private Logger logger = Logger.getLogger(EventServiceImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
    // Search
    public PagedResultDTO<Event> search(int pageSize, int pageIndex, String name) {
		 logger.debug("search('" + name + "')" );
		 
		 String countQuery = 	"select count(*) " +
				         		"from Event e " +
				         		"where e.name like '%" + name + "%' ";

		 DetachedCriteria criteria = DetachedCriteria.forClass(Event.class);
		 criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		 criteria.addOrder(Order.asc("name"));

		 return DbUtil.search(hibernateTemplate, pageSize, pageIndex, countQuery, null, criteria); 
    }
}
