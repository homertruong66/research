package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * homertruong
 */

public class UniversityCreateModel implements Serializable {

    private static final long serialVersionUID = -1690484851789564439L;

    @JsonProperty
    @NotNull(message = "'Name' can't be null!")
    @NotEmpty
    private String name;

    @JsonProperty("countryId")
    @NotNull
    private String countryId;

    public void escapeHtml () {
        this.name = HtmlUtils.htmlEscape(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "UniversityCreateModel{" +
                "name='" + name + '\'' +
                ", countryId='" + countryId + '\'' +
                '}';
    }
}
