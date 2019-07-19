package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

/**
 * homertruong
 */

public class Guide extends BaseEntityExtensible {

    private String content;
    private String note;
    private String target;      // SUBS_ADMIN, AFFILIATE
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Guide{" +
                "content='" + content + '\'' +
                ", note='" + note + '\'' +
                ", target='" + target + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
