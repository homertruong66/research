package com.rms.rms.common.view_model;

import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class SubsCommissionConfigUpdateModel {

    private Boolean isEnabled;  // CFE, COPPR, COPQ, CAD, COAN, COSAL, COOV, COPG, COASV, COPS

    private Double commission;  // CAD, COSAL

    private Set<ConditionCommissionConfigUpdateModel> conditionCommissionConfigs = new HashSet<>(); // COPPR, COPQ, COOV, COASV

    private Set<LayerCommissionConfigUpdateModel> layerCommissionConfigs = new HashSet<>(); // COAN

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";

        if (commission != null) {
            if (commission.doubleValue() > 1) {
                errors += "'commission' can't be > 1 ! && ";
            }

            if (commission.doubleValue() < 0) {
                errors += "'commission' can't be < 0 ! && ";
            }
        }

        return errors.equals("") ? null : errors;
    }

    //
    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Set<ConditionCommissionConfigUpdateModel> getConditionCommissionConfigs() {
        return conditionCommissionConfigs;
    }

    public void setConditionCommissionConfigs(Set<ConditionCommissionConfigUpdateModel> conditionCommissionConfigs) {
        this.conditionCommissionConfigs = conditionCommissionConfigs;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Set<LayerCommissionConfigUpdateModel> getLayerCommissionConfigs() {
        return layerCommissionConfigs;
    }

    public void setLayerCommissionConfigs(Set<LayerCommissionConfigUpdateModel> layerCommissionConfigs) {
        this.layerCommissionConfigs = layerCommissionConfigs;
    }

    @Override
    public String toString() {
        return "SubsCommissionConfigUpdateModel{" +
                "isEnabled=" + isEnabled +
                ", commission=" + commission +
                ", conditionCommissionConfigs=" + conditionCommissionConfigs +
                ", layerCommissionConfigs=" + layerCommissionConfigs +
                '}';
    }
}
