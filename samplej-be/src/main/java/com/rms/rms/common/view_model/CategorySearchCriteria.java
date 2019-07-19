package com.rms.rms.common.view_model;

public class CategorySearchCriteria {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategorySearchCriteria{" +
                "name='" + name + '\'' +
                '}';
    }
}
