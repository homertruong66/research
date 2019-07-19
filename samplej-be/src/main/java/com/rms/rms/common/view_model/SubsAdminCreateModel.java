package com.rms.rms.common.view_model;

/**
 * homertruong
 */

public class SubsAdminCreateModel extends PersonCreateModel {

    private String feUrl;

    public String validate() {
        return super.validate();
    }

    public String getFeUrl() {
        return feUrl;
    }

    public void setFeUrl(String feUrl) {
        this.feUrl = feUrl;
    }

    @Override
    public String toString() {
        return "SubsAdminCreateModel{" +
                "feUrl='" + feUrl + '\'' +
                '}';
    }
}
