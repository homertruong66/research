package com.rms.rms.common.view_model;

/**
 * homertruong
 */

public class AffiliateSearchCriteria extends PersonSearchCriteria {

    private Boolean forNetwork;

    public Boolean getForNetwork() {
        return forNetwork;
    }

    public void setForNetwork(Boolean forNetwork) {
        this.forNetwork = forNetwork;
    }

    @Override
    public String toString() {
        return "AffiliateSearchCriteria{" +
                "forNetwork=" + forNetwork +
                "} - " + super.toString();
    }
}
