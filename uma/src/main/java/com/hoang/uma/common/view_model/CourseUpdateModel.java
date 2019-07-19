package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * homertruong
 */

public class CourseUpdateModel implements Serializable {

    private static final long serialVersionUID = 6696625034681327718L;

    @JsonProperty
    private String name;

    @JsonProperty("numberOfCredits")
    private int numberOfCredits = 0;

    @JsonProperty
    @Min(value = 0, message = "'Version' must be greater than or equal 0!")
    private int version;

    public void escapeHtml () {
        this.name = HtmlUtils.htmlEscape(this.name);
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "CourseUpdateModel{" +
                "name='" + name + '\'' +
                ", numberOfCredits=" + numberOfCredits +
                ", version=" + version +
                '}';
    }
}
