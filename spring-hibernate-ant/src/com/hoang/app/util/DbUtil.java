package com.hoang.app.util;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.hoang.app.dto.PagedResultDTO;

/**
 * 
 * @author Hoang Truong
 */

public class DbUtil {

	@SuppressWarnings("unchecked")
	public static int count(HibernateTemplate hibernateTemplate, String hql, Object [] queryArgs) {
        List<Object> results = null;
        if (queryArgs == null) {
            results = hibernateTemplate.find(hql);
        }
        else {
            results = hibernateTemplate.find(hql, queryArgs);
        }
        
        if (results == null || results.size() == 0) {
            return 0;
        }

        return ((Long) results.get(0)).intValue();
    }
	
    @SuppressWarnings("unchecked")
	public static <T> PagedResultDTO<T> search(HibernateTemplate hibernateTemplate, 
    							 int pageSize, int pageIndex,
                                 String countQuery, Object [] queryArgs,
                                 DetachedCriteria criteria)
    {
        // get number of rows
        int rowCount = count(hibernateTemplate, countQuery, queryArgs);

        // get rows in pageIndex
        int firstResult = (pageIndex - 1) * pageSize;
        int maxResults = pageSize;        
        List<T> pagedResults = hibernateTemplate.findByCriteria(criteria,
                                                        firstResult, maxResults);

        PagedResultDTO<T> pr = new PagedResultDTO<T>();
        pr.setTotalRows(rowCount);
        pr.setPageSize(pageSize);
        pr.setNumOfPages((int) (Math.ceil(((double) rowCount) / ((double) pageSize))));
        pr.setPageIndex(pageIndex);
        int firstRowIndexInPage = (pageIndex - 1) * pageSize + 1;        
        if (rowCount == 0) {
            firstRowIndexInPage = 0;
        }
        int lastRowIndexInPage = pageIndex * pageSize;
        if (lastRowIndexInPage > rowCount) {
            lastRowIndexInPage = rowCount;
        }
        pr.setFirstRowIndexInPage(firstRowIndexInPage);
        pr.setLastRowIndexInPage(lastRowIndexInPage);
        pr.setHasPreviousPage(pageIndex > 1);
        pr.setHasNextPage(pageIndex < pr.getNumOfPages());
        pr.setPagedResults(pagedResults);

        return pr;
    }
}
