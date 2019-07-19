package com.hoang.app.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoang.app.dto.PagedResultDTO;

/**
 * 
 * @author Hoang Truong
 */

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class CrudServiceImpl implements CrudService {

	private Logger logger = Logger.getLogger(CrudServiceImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;	
	
// 	@Autowired
//	private DataSource dataSource;	
	
	public <T> T create(T pdo) {
		logger.debug("create(" + pdo.getClass() + ")" );
		hibernateTemplate.saveOrUpdate(pdo);
		return pdo;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public <T> T read(Class<T> clazz, Serializable id) {
		logger.debug("read(" + clazz.getClass() + ", " + id + ")" );
		return (T) hibernateTemplate.get(clazz, id);
	}
	
	public <T> boolean has(Class<T> clazz, Serializable id) {
		return read(clazz, id) != null;
	}
	
	public <T> T update(T pdo) {
		logger.debug("update(" + pdo.getClass() + ")" );
		hibernateTemplate.saveOrUpdate(pdo);
		return pdo;
	}
	
	public <T> void delete (T pdo) {
		logger.debug("delete(" + pdo.getClass() + ")" );
		hibernateTemplate.delete(pdo);		
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	@SuppressWarnings("unchecked")
	public <T> Long countAll(Class<T> clazz) {
		logger.debug("countAll(" + clazz.getClass() + ")" );
        List<T> results = hibernateTemplate.find("select count(*) from " + clazz.getSimpleName());
        if (results == null || results.size() == 0) {
            return 0l;
        }

        return ((Long) results.get(0)).longValue();
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(Class<T> clazz) {
		logger.debug("getAll(" + clazz.getClass() + ")" );		
		return (List<T>) hibernateTemplate.find("from " + clazz.getSimpleName());
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	@SuppressWarnings("unchecked")
	public <T> PagedResultDTO<T> page(Class<T> clazz, int pageSize, int pageIndex) {
		logger.debug("page(" + pageSize + ", " + pageIndex + ")" );		
		
		Long rowCount = countAll(clazz);
        int firstResult = (pageIndex - 1) * pageSize;
        int maxResults = pageSize;
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        List<T> list = hibernateTemplate.findByCriteria(criteria, firstResult, maxResults);

        PagedResultDTO<T> pr = new PagedResultDTO<T>();
        pr.setTotalRows(rowCount.intValue());
        pr.setPageSize(pageSize);
        pr.setNumOfPages((int) (Math.ceil(((double) rowCount) / ((double) pageSize))));
        pr.setPageIndex(pageIndex);
        int firstRowIndexInPage = (pageIndex - 1) * pageSize + 1;        
        if (rowCount == 0) {
            firstRowIndexInPage = 0;
        }
        int lastRowIndexInPage = pageIndex * pageSize;
        if (lastRowIndexInPage > rowCount) {
            lastRowIndexInPage = rowCount.intValue();
        }
        pr.setFirstRowIndexInPage(firstRowIndexInPage);
        pr.setLastRowIndexInPage(lastRowIndexInPage);
        pr.setHasPreviousPage(pageIndex > 1);
        pr.setHasNextPage(pageIndex < pr.getNumOfPages());
        pr.setPagedResults(list);

        return pr;				
	}	
}

