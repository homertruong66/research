package com.hoang.module.mail.service;

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
import com.hoang.module.mail.domain.Item;

/**
*
* @author Hoang Truong
*/

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class ItemServiceImpl implements ItemService  {  
	
	private Logger logger = Logger.getLogger(ItemServiceImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
    // Search
    public PagedResultDTO<Item> searchByMailer(int pageSize, int pageIndex,
                                            	Long mailerId, String name)
    {
		 logger.debug("searchByMailer('" + mailerId + "', '" + name + "')" );
		 
		 String countQuery = 	"select count(*) " +
				         		"from Item i " +
				         		"where i.mailer.id = " + mailerId + " " +
				         		"and i.name like '%" + name + "%' ";

		 DetachedCriteria criteria = DetachedCriteria.forClass(Item.class);
		 criteria.add(Restrictions.eq("mailer.id", mailerId));
		 criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		 criteria.addOrder(Order.asc("name"));

		 return DbUtil.search(hibernateTemplate, pageSize, pageIndex, countQuery, null, criteria); 
    }
}
