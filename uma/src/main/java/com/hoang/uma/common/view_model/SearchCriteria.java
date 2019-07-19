package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * homertruong
 */

public class SearchCriteria {

    @JsonProperty("sortName")
    private String sortName;

    @JsonProperty("sortDirection")
    private String sortDirection = "asc";

    @JsonProperty("pageIndex")
    @Min(value = 1, message = "pageIndex must be greater than 1")
    @Max(value = Integer.MAX_VALUE, message = "pageIndex can't be greater than " + Integer.MAX_VALUE)
    private int pageIndex = 1;

    @JsonProperty("pageSize")
    @Min(value = 1, message = "pageSize must be greater than 1")
    @Max(value = Integer.MAX_VALUE, message = "pageSize can't be greater than " + Integer.MAX_VALUE)
    private int pageSize = 5;

    public String getSortName () {
        return sortName;
    }

    public void setSortName (String sortName) {
        this.sortName = sortName;
    }

    public String getSortDirection () {
        return sortDirection;
    }

    public void setSortDirection (String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public int getPageIndex () {
        return pageIndex;
    }

    public void setPageIndex (int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize () {
        return pageSize;
    }

    public void setPageSize (int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset () {
        return (this.pageIndex - 1) * this.pageSize;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "sortName='" + sortName + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                '}';
    }
}
