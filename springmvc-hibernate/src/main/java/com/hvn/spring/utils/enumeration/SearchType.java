package com.hvn.spring.utils.enumeration;

public enum SearchType {
	
	EQUALS("="), LIKE("LIKE");
	
	private String searchType;
	
	private SearchType(String searchType) {
		this.searchType = searchType;
	}
	
	public String getSearchType() {
		return this.searchType;
	}
}
