package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.HtmlUtils;

import java.io.Serializable;

/**
 * homertruong
 */

public class TeacherCreateModel extends UserCreateModel implements Serializable {

    private static final long serialVersionUID = 4508315763373487785L;

    @JsonProperty
    private String degree;

    public void escapeHtml () {
        super.escapeHtml();
        this.degree = HtmlUtils.htmlEscape(this.degree);
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "TeacherCreateModel{" +
                ", degree=" + degree +
                "} - " + super.toString();
    }
}
