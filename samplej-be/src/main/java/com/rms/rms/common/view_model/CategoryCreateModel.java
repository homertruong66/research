package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class CategoryCreateModel {

    private String name;

    public void escapeHtml() {
        this.name = HtmlUtils.htmlEscape(this.name, "UTF-8");
    }

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(name)) {
            errors += "'name' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryCreateModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
