package com.hvn.spring.utils.enumeration;

public enum SortType {
	
	ASC("asc"), DESC("desc");
	
	private String sortType;
	
	private SortType(String sortType) {
		this.sortType = sortType;
	}
	
	public String getSortType() {
		return this.sortType;
	}
}
