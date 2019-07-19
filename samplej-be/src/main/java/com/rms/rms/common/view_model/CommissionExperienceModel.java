package com.rms.rms.common.view_model;

public class CommissionExperienceModel {

    private Double cad;
    private Double cdc;
    private Double coanReferrer1;
    private Double coanReferrer2;
    private Double coanSeller;
    private Double coov;
    private Double copg;
    private Double coppr;
    private Double copq;
    private Double cops;
    private OrderExperienceModel order;

    public void escapeHtml() {}

    public String validate() {
        String errors = "";

        if (order == null) {
            errors += "'order' can't be null! && ";
        }
        else {
            String orderErrors = order.validate();
            if (orderErrors != null) {
                errors += orderErrors;
            }
        }

        return errors.equals("") ? null : errors;
    }

    //
    public Double getCad() {
        return cad;
    }

    public void setCad(Double cad) {
        this.cad = cad;
    }

    public Double getCdc() {
        return cdc;
    }

    public void setCdc(Double cdc) {
        this.cdc = cdc;
    }

    public Double getCoanReferrer1() {
        return coanReferrer1;
    }

    public void setCoanReferrer1(Double coanReferrer1) {
        this.coanReferrer1 = coanReferrer1;
    }

    public Double getCoanReferrer2() {
        return coanReferrer2;
    }

    public void setCoanReferrer2(Double coanReferrer2) {
        this.coanReferrer2 = coanReferrer2;
    }

    public Double getCoanSeller() {
        return coanSeller;
    }

    public void setCoanSeller(Double coanSeller) {
        this.coanSeller = coanSeller;
    }

    public Double getCoov() {
        return coov;
    }

    public void setCoov(Double coov) {
        this.coov = coov;
    }

    public Double getCopg() {
        return copg;
    }

    public void setCopg(Double copg) {
        this.copg = copg;
    }

    public Double getCoppr() {
        return coppr;
    }

    public void setCoppr(Double coppr) {
        this.coppr = coppr;
    }

    public Double getCopq() {
        return copq;
    }

    public void setCopq(Double copq) {
        this.copq = copq;
    }

    public Double getCops() {
        return cops;
    }

    public void setCops(Double cops) {
        this.cops = cops;
    }

    public OrderExperienceModel getOrder() {
        return order;
    }

    public void setOrder(OrderExperienceModel order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "CommissionExperienceModel{" +
                "cad=" + cad +
                ", cdc=" + cdc +
                ", coanReferrer1=" + coanReferrer1 +
                ", coanReferrer2=" + coanReferrer2 +
                ", coanSeller=" + coanSeller +
                ", coov=" + coov +
                ", copg=" + copg +
                ", coppr=" + coppr +
                ", copq=" + copq +
                ", cops=" + cops +
                ", order=" + order +
                '}';
    }
}
