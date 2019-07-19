package com.hoang.app.command;

/**
 * 
 * @author Hoang Truong
 *
 */

public class VoteSearchCommand {
    private int page = 1;
    private Long eventId;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

