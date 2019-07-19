package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * homertruong
 */

public class UniversityUpdateModel implements Serializable {

    private static final long serialVersionUID = -1922541490754776621L;

    @JsonProperty
    private String name;

    @JsonProperty("countryId")
    private String countryId;

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

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "UniversityUpdateModel{" +
                "name='" + name + '\'' +
                ", countryId='" + countryId + '\'' +
                ", version=" + version +
                '}';
    }
}
