package com.rms.rms.common.view_model;

import java.io.Serializable;
import java.util.List;

public class PriorityGroupAffiliatesAssignModel implements Serializable {

    private List<String> affiliateIds;

    public List<String> getAffiliateIds () {
        return affiliateIds;
    }

    public void setAffiliateIds (List<String> affiliateIds) {
        this.affiliateIds = affiliateIds;
    }

    @Override
    public String toString () {
        return "PriorityGroupAffiliatesAssignModel{" +
               "affiliateIds='" + affiliateIds +
               '}';
    }

    public String validate () {
        String errors = "";

        if (affiliateIds == null || affiliateIds.size() == 0) {
            errors += "'affiliateIds' can't be empty! && ";
        }

        return errors.equals("") ? null : errors;
    }
}