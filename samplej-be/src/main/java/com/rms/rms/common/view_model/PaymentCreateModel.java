package com.rms.rms.common.view_model;

public class PaymentCreateModel {

    private String affiliateId;
    private String subscriberId;
    private Double value;

    public String getAffiliateId () {
        return affiliateId;
    }

    public void setAffiliateId (String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public String getSubscriberId () {
        return subscriberId;
    }

    public void setSubscriberId (String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Double getValue () {
        return value;
    }

    public void setValue (Double value) {
        this.value = value;
    }

    @Override
    public String toString () {
        return "PaymentCreateModel{" +
               "affiliateId='" + affiliateId + '\'' +
               ", subscriberId='" + subscriberId + '\'' +
               ", value=" + value +
               '}';
    }

    public String validate () {
        String errors = "";

        if ( value == null ) {
            errors += "'value' can't be empty! && ";
        }
        else if (value <= 0 ) {
            errors += "'value' can't be less than or equal 0! && ";
        }
        else if (value > Double.MAX_VALUE ) {
            errors += "'value' can't be greater than " + Double.MAX_VALUE + "! && ";
        }

        return errors.equals("") ? null : errors;
    }
}
