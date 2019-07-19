package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

public class PriorityGroupAffiliate extends BaseEntityExtensible {

    private Affiliate     affiliate;
    private String        affiliateId;
    private PriorityGroup priorityGroup;
    private String        priorityGroupId;

    public Affiliate getAffiliate () {
        return affiliate;
    }

    public void setAffiliate (Affiliate affiliate) {
        this.affiliate = affiliate;
    }

    public String getAffiliateId () {
        return affiliateId;
    }

    public void setAffiliateId (String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public PriorityGroup getPriorityGroup () {
        return priorityGroup;
    }

    public void setPriorityGroup (PriorityGroup priorityGroup) {
        this.priorityGroup = priorityGroup;
    }

    public String getPriorityGroupId () {
        return priorityGroupId;
    }

    public void setPriorityGroupId (String priorityGroupId) {
        this.priorityGroupId = priorityGroupId;
    }

    @Override
    public String toString () {
        return "PriorityGroupAffiliate{" +
               "affiliate=" + affiliate +
               ", affiliateId='" + affiliateId + '\'' +
               ", priorityGroup=" + priorityGroup +
               ", priorityGroupId='" + priorityGroupId + '\'' +
               '}';
    }
}
