package com.rms.rms.common.view_model;

public class SubscriberExtendModel {

    private Integer days;

    public String validate() {
        String errors = "";

        if (days == null || days <= 0) {
            errors += "'days' value must bigger than 0";
        }

        return errors.equals("") ? null : errors;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "SubscriberExtendModel{" +
                "days=" + days +
                '}';
    }
}
