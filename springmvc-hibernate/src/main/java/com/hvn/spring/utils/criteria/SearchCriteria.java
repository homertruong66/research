package com.hvn.spring.utils.criteria;

import com.hvn.spring.utils.enumeration.SearchType;
import com.hvn.spring.utils.enumeration.SortType;

public class SearchCriteria {
	private String searchBy;
	private String searchType;
	private String searchText;
	private String sortBy;
	private String sortDirection;
	private Integer pageIndex;
	private Integer pageSize;

	public SearchCriteria() {
		this.searchType = SearchType.EQUALS.getSearchType();
		this.sortDirection = SortType.ASC.getSortType();
	}
	
	public SearchCriteria(String searchBy, String searchText, String sortBy,
			String sortDirection, Integer pageIndex, Integer pageSize) {
		this.searchBy = searchBy;
		this.searchText = searchText;
		this.searchType = SearchType.EQUALS.getSearchType();
		this.sortBy = sortBy;
		this.sortDirection = sortDirection;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}
	
	public void removePagingInfo() {
		this.pageIndex = null;
		this.pageSize = null;
	}
	
	public String getSearchBy() {
		return searchBy;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public String getSortBy() {
		return sortBy;
	}
	
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public Integer getStartIndex() {
		return pageIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.pageIndex = startIndex;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
