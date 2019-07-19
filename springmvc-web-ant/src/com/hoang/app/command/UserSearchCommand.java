package com.hoang.app.command;

/**
*
* @author Hoang Truong
*/

public class UserSearchCommand {    
    private int page = 1;
    private String username;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
}
