package com.hoang.uma.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * homertruong
 */

public class SearchResultDto<T> implements Serializable {

    private static final long serialVersionUID = -711133640963952869L;

    private long totalRows;
    private int pageSize;
    private int numOfPages;
    private int pageIndex;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private List<T> list;

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void buildPagingParams (int pageIndex, int pageSize, long rowCount) {
        int numOfPages = (int) (Math.ceil(((double) rowCount) / ((double) pageSize)));
        
        this.setTotalRows(rowCount);
        this.setPageSize(pageSize);
        this.setNumOfPages(numOfPages);
        this.setPageIndex(pageIndex);
        this.setHasPreviousPage(pageIndex > 1);
        this.setHasNextPage(pageIndex < numOfPages);
    }

}
