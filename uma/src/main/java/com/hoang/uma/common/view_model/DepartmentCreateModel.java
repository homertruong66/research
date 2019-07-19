package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.util.HtmlUtils;

import java.io.Serializable;

/**
 * homertruong
 */

public class DepartmentCreateModel implements Serializable {

    private static final long serialVersionUID = -6314446151333234367L;

    @JsonProperty
    @NotEmpty(message = "'Name' can't be empty!")
    private String name;

    public void escapeHtml () {
        this.name = HtmlUtils.htmlEscape(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DepartmentCreateModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
