package com.rms.rms.persistence;

import org.hibernate.criterion.Criterion;

import java.util.List;

/**
 * homertruong
 */

public class SearchSortPageCriteria {

    // search
    private List<Criterion> searchCriteria;

    // sort
    private String sortName = null;
    private boolean isSortAsc = true;

    // page
    private int pageIndex = 1;
    private int pageSize = Integer.valueOf(System.getProperty("sql.query.defaultPageSize", "100"));

    public SearchSortPageCriteria() {
    }

    public SearchSortPageCriteria(List<Criterion> searchCriteria, String sortName, boolean isSortAsc, int pageIndex, int pageSize) {
        this.searchCriteria = searchCriteria;
        this.sortName = sortName;
        this.isSortAsc = isSortAsc;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public List<Criterion> getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(List<Criterion> searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

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
        isSortAsc = sortAsc;
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

    @Override
    public String toString() {
        return "SearchSortPageCriteria{" +
                "searchCriteria=" + searchCriteria +
                ", sortName='" + sortName + '\'' +
                ", isSortAsc=" + isSortAsc +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                '}';
    }
}
