package com.hoang.app.command;

/**
 * 
 * @author Hoang Truong
 *
 */

public class PlaceSearchCommand {    
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
