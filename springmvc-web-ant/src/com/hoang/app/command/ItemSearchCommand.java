package com.hoang.app.command;

/**
 * 
 * @author Hoang Truong
 *
 */

public class ItemSearchCommand {
    private int page = 1;
    private Long mailerId;
    private String name;

    public Long getMailerId() {
        return mailerId;
    }

    public void setMailerId(Long mailerId) {
        this.mailerId = mailerId;
    }

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
