package com.rms.rms.common.view_model;

import org.springframework.web.util.HtmlUtils;

public class CategoryUpdateModel {

    private String name;

    public void escapeHtml() {
        this.name = HtmlUtils.htmlEscape(this.name, "UTF-8");
    }

    public String validate() {
        return null;
    }

    //
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryUpdateModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
