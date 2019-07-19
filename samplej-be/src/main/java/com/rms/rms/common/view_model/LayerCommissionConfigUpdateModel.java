package com.rms.rms.common.view_model;

/**
 * homertruong
 */

public class LayerCommissionConfigUpdateModel {

    private Double commission;
    private Integer layer;

    public void escapeHtml() {
    }

    public String validate() {
        String errors = "";
        if (commission == null) {
            errors += "'commission' can't be null! && ";
        }

        if (layer == null) {
            errors += "'layer' can't be null! && ";
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

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    @Override
    public String toString() {
        return "LayerCommissionConfigUpdateModel{" +
                "commission=" + commission +
                ", layer=" + layer +
                '}';
    }
}
