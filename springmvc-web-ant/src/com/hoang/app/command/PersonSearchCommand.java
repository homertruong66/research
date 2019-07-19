package com.hoang.app.command;

/**
 * 
 * @author Hoang Truong
 *
 */

public class PersonSearchCommand {    
    private int page = 1;
    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    
}
