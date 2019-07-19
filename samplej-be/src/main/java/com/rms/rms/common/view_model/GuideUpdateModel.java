package com.rms.rms.common.view_model;

/**
 * homertruong
 */

public class GuideUpdateModel {

    private String content;
    private String note;
    private String title;

    public void escapeHtml() {
        //this.content = HtmlUtils.htmlEscape(this.content, "UTF-8");       // this is HTML
        //this.note = HtmlUtils.htmlEscape(this.note, "UTF-8");             // this is HTML
        //this.title= HtmlUtils.htmlEscape(this.title, "UTF-8");            // this is HTML
    }

    public String validate() {
        return null;
    }

    //
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "GuideUpdateModel{" +
                "content='" + content + '\'' +
                ", note='" + note + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
