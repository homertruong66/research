package com.rms.rms.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Map;

/**
 * homertruong
 */

public class SearchCriteria<T> implements Serializable {

    private static final long serialVersionUID = -7501057241800803697L;

    public static final int MAX_PAGE_SIZE = Integer.valueOf(System.getProperty("sql.query.maxPageSize", "1000"));

    // search properties
    private T criteria;
    private CustomCriteria customCriteria = new CustomCriteria();

    // sort properties
    private String sortName = null;
    private boolean isSortAsc = true;

    // page properties
    private int pageIndex = 1;
    private int pageSize = Integer.valueOf(System.getProperty("sql.query.defaultPageSize", "100"));


    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public boolean isSortAsc() {
        return isSortAsc;
    }

    public void setSortAsc(boolean sortAsc) {
        this.isSortAsc = sortAsc;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getCriteria() {
        return criteria;
    }

    public void setCriteria(T criteria) {
        this.criteria = criteria;
    }

    public CustomCriteria getCustomCriteria() {
        return customCriteria;
    }

    public void setCustomCriteria(CustomCriteria props) {
        if (props != null) customCriteria.putAll(props);
        else customCriteria.clear();
    }

    @JsonIgnore // add this for testing API
    public void setCustomCriteria(Map<String, Object> props) {
        if (props != null) customCriteria.putAll(props);
        else customCriteria.clear();
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", sortName='" + sortName + '\'' +
                ", isSortAsc=" + isSortAsc +
                ", criteria=" + criteria +
                ", customCriteria=" + customCriteria +
                '}';
    }

}
