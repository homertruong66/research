package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * homertruong
 */

public class TeacherUpdateModel implements Serializable {

    private static final long serialVersionUID = 5267601784650815938L;

    @JsonProperty
    private String name;

    @JsonProperty
    private int age;

    @JsonProperty
    private String degree;

    @JsonProperty
    @Min(value = 0, message = "'Version' must be greater than or equal 0!")
    private int version;

    public void escapeHtml () {
        this.name = HtmlUtils.htmlEscape(this.name);
        this.degree = HtmlUtils.htmlEscape(this.degree);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "TeacherUpdateModel{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", degree='" + degree + '\'' +
                ", version=" + version +
                '}';
    }
}
