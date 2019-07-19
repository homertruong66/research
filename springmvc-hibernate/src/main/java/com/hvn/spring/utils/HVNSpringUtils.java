package com.hvn.spring.utils;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;
import com.hvn.spring.utils.criteria.SearchCriteria;
import com.hvn.spring.utils.enumeration.SearchType;
import com.hvn.spring.utils.enumeration.SortType;

public class HVNSpringUtils {

	public static DetachedCriteria createDetachedCriteria(SearchCriteria searchCriteria, Class<?> clazz) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(clazz);
		
		// search by
		if (searchCriteria.getSearchBy() != null && !searchCriteria.getSearchBy().isEmpty()) {
			if (SearchType.EQUALS.getSearchType().equals(searchCriteria.getSearchType())) {
				detachedCriteria.add(Restrictions.eq(searchCriteria.getSearchBy(), searchCriteria.getSearchText()));
			
			} else if (SearchType.LIKE.getSearchType().equalsIgnoreCase(searchCriteria.getSearchType())) {
				detachedCriteria.add(Restrictions.like(searchCriteria.getSearchBy(), searchCriteria.getSearchText(), MatchMode.ANYWHERE));
			}
		}
		
		// sort by
		if (searchCriteria.getSortBy() != null && !searchCriteria.getSortBy().isEmpty()) {
			
			if (SortType.ASC.getSortType().equalsIgnoreCase(searchCriteria.getSortDirection())) {
				detachedCriteria.addOrder(Order.asc(searchCriteria.getSortBy()));
				
			} else {
				detachedCriteria.addOrder(Order.desc(searchCriteria.getSortBy()));
			}

		}
		
		return detachedCriteria;
	}
	
	public static SearchCriteria createSearchCriteria(String criteriaStr) {
		// get search criteria
    	SearchCriteria searchCriteria;
    	Gson gon = new Gson();
    	if (criteriaStr != null && !criteriaStr.isEmpty()) {
    		searchCriteria = gon.fromJson(criteriaStr, SearchCriteria.class);
    		
    	} else {
    		searchCriteria = new SearchCriteria();
    	}
    	
    	return searchCriteria;
	}
	
}
