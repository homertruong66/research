package com.hoang.srj.dto;

import java.util.List;

public class SearchResult<E> {
    private int totalRows;
    private int pageSize;
    private int numOfPages;
    private int pageIndex;
    private int firstRowIndexInPage;
    private int lastRowIndexInPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private List<E> pagedResults;

    public int getFirstRowIndexInPage() {
        return firstRowIndexInPage;
    }

    public void setFirstRowIndexInPage(int firstRowIndexInPage) {
        this.firstRowIndexInPage = firstRowIndexInPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public int getLastRowIndexInPage() {
        return lastRowIndexInPage;
    }

    public void setLastRowIndexInPage(int lastRowIndexInPage) {
        this.lastRowIndexInPage = lastRowIndexInPage;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
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

    public List<E> getPagedResults() {
        return pagedResults;
    }

    public void setPagedResults(List<E> pagedResults) {
        this.pagedResults = pagedResults;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}
