package com.hoang.srj.view_model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

public class UserSearchCriteria implements Serializable {

    private static final long serialVersionUID = 4279351040702774201L;

    private String name;

    private String sortName;

    private String sortDirection = "asc";

    @Min(value = 1, message = "pageIndex must be greater than 1")
    @Max(value = Integer.MAX_VALUE, message = "pageIndex can't be greater than " + Integer.MAX_VALUE)
    private int pageIndex = 1;

    @Min(value = 1, message = "pageSize must be greater than 1")
    @Max(value = Integer.MAX_VALUE, message = "pageSize can't be greater than " + Integer.MAX_VALUE)
    private int pageSize = 5;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

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
}
