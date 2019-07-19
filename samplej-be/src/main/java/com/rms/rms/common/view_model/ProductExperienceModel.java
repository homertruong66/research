package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

public class ProductExperienceModel {

    private String name;

    public void escapeHtml() {}

    public String validate() {
        String errors = "";

        if (StringUtils.isBlank(name)) {
            errors += "'name' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
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
        return "ProductExperienceModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
