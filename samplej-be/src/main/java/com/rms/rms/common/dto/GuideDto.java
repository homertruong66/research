package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class GuideDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 847458590285950565L;

    private String content;
    private String note;
    private String target;
    private String title;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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
        return "GuideDto{" +
                "content='" + content + '\'' +
                ", note='" + note + '\'' +
                ", target='" + target + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}