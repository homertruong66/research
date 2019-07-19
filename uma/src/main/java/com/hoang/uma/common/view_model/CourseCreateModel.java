package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * homertruong
 */

public class CourseCreateModel implements Serializable {

    private static final long serialVersionUID = 5357544048799603171L;

    @JsonProperty
    @NotEmpty(message = "'Name' can't be empty!")
    private String name;

    @JsonProperty("numberOfCredits")
    @Min(value = 1, message = "'numberOfCredits' must be greater than 0!")
    @Max(value = 3, message = "'numberOfCredits' must be less than 4!")
    private int numberOfCredits;

    public void escapeHtml () {
        this.name = HtmlUtils.htmlEscape(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    @Override
    public String toString() {
        return "CourseCreateModel{" +
                "name='" + name + '\'' +
                ", numberOfCredits=" + numberOfCredits +
                '}';
    }
}
