package com.hoang.app.command;

/*
 * Project: YJF
 *
 * WHEN        WHO            WHAT           DESCRIPTION
 * 06/06/09    Hoang Truong   Creation   	  <…>
 *
 * Copyright 2009 by YGS Corp.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of YGS Corp. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with YGS Corp.
 */

public class OrganizationSearchCommand {    
    private int page = 1;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    
    
}
