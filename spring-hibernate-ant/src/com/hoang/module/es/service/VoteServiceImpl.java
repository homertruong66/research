package com.hoang.module.es.service;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.util.DbUtil;
import com.hoang.module.es.domain.Vote;

/**
*
* @author Hoang Truong
*/

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class VoteServiceImpl implements VoteService  { 
	
	private Logger logger = Logger.getLogger(VoteServiceImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
    // Search
    public PagedResultDTO<Vote> searchByEvent(int pageSize, int pageIndex, Long eventId)  {
		 logger.debug("searchByEvent('" + eventId + "')" );
		 
		 String countQuery = 	"select count(*) " +
				         		"from Vote v " +
				         		"where v.event.id = " + eventId;

		 DetachedCriteria criteria = DetachedCriteria.forClass(Vote.class);
		 criteria.add(Restrictions.eq("event.id", eventId));
		 criteria.addOrder(Order.desc("date"));

		 return DbUtil.search(hibernateTemplate, pageSize, pageIndex, countQuery, null, criteria); 
    }
}
