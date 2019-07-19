package com.hoang.linkredirector.view_model;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class UserSearchCriteria implements Serializable {

    private static final long serialVersionUID = -2621089893136317367L;

    @QueryParam("name")
    @DefaultValue("")
    private String name;

    @QueryParam("sort_name")
    @DefaultValue("id")
    private String sortName;

    @QueryParam("sort_direction")
    @DefaultValue("asc")
    private String sortDirection;

    @Min(value = 1, message = "page_index must be greater than 1")
    @Max(value = Integer.MAX_VALUE, message = "page_index can't be greater than " + Integer.MAX_VALUE)
    @DefaultValue("1")
    @QueryParam("page_index")
    private int pageIndex;

    @QueryParam("page_size")
    @DefaultValue("50")
    private int pageSize;

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
