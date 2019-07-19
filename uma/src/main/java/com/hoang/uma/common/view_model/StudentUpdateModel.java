package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * homertruong
 */

public class StudentUpdateModel implements Serializable {

    private static final long serialVersionUID = 2345147072984530149L;

    @JsonProperty
    private String name;

    @JsonProperty
    private int age;

    @JsonProperty
    private int year;

    @JsonProperty
    @Min(value = 0, message = "'Version' must be greater than or equal 0!")
    private int version;

    public void escapeHtml () {
        this.name = HtmlUtils.htmlEscape(this.name);
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "StudentUpdateModel{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", year=" + year +
                ", version=" + version +
                '}';
    }
}
