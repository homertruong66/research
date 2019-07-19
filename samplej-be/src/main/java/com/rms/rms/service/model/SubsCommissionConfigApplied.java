package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.Date;

/**
 * homertruong
 */

public class SubsCommissionConfigApplied extends BaseEntityExtensible {

    private static final String TYPE_CFE   = "CFE";  // determine seller
    private static final String NAME_CFE   = "Commission Forever";

    private static final String TYPE_COPS  = "COPS";
    private static final String NAME_COPS  = "Commission On Product Set";

    private static final String TYPE_COPPR = "COPPR";
    private static final String NAME_COPPR = "Commission On Product Price Range";

    private static final String TYPE_COPQ = "COPQ";
    private static final String NAME_COPQ = "Commission On Product Quantity";

    private static final String TYPE_CAD   = "CAD";
    private static final String NAME_CAD   = "Commission As Default";

    private static final String TYPE_COAN  = "COAN";
    private static final String NAME_COAN  = "Commission On Affiliate Network";

    private static final String TYPE_COSAL = "COSAL";
    private static final String NAME_COSAL = "Commission On Same Affiliate Level";

    private static final String TYPE_COOV  = "COOV";
    private static final String NAME_COOV  = "Commission On Order Value";

    private static final String TYPE_COPG  = "COPG";
    private static final String NAME_COPG  = "Commission On Priority Group";

    private static final String TYPE_COASV = "COASV";
    private static final String NAME_COASV = "Commission On Affiliate Sales Value";

    private Double  commission;
    private Date    endDate;
    private Integer lowerCondition;
    private String  name;
    private String productId;
    private Date    startDate;
    private String subscriberId;
    private String  type;
    private Integer upperCondition;

    public Double getCommission () {
        return commission;
    }

    public void setCommission (Double commission) {
        this.commission = commission;
    }

    public Date getEndDate () {
        return endDate;
    }

    public void setEndDate (Date endDate) {
        this.endDate = endDate;
    }

    public Integer getLowerCondition () {
        return lowerCondition;
    }

    public void setLowerCondition (Integer lowerCondition) {
        this.lowerCondition = lowerCondition;
    }

    public String getName () {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getStartDate () {
        return startDate;
    }

    public void setStartDate (Date startDate) {
        this.startDate = startDate;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        if (type == null) {
            return;
        }

        this.type = type;

        switch (this.type) {
            case TYPE_CFE:
                this.name = NAME_CFE;
                break;

            case TYPE_COPS:
                this.name = NAME_COPS;
                break;

            case TYPE_COPPR:
                this.name = NAME_COPPR;
                break;

            case TYPE_COPQ:
                this.name = NAME_COPQ;
                break;

            case TYPE_CAD:
                this.name = NAME_CAD;
                break;

            case TYPE_COAN:
                this.name = NAME_COAN;
                break;

            case TYPE_COSAL:
                this.name = NAME_COSAL;
                break;

            case TYPE_COOV:
                this.name = NAME_COOV;
                break;

            case TYPE_COPG:
                this.name = NAME_COPG;
                break;

            case TYPE_COASV:
                this.name = NAME_COASV;
                break;
        }
    }

    public Integer getUpperCondition () {
        return upperCondition;
    }

    public void setUpperCondition (Integer upperCondition) {
        this.upperCondition = upperCondition;
    }

    @Override
    public String toString() {
        return "SubsCommissionConfigApplied{" +
                "commission=" + commission +
                ", endDate=" + endDate +
                ", lowerCondition=" + lowerCondition +
                ", name='" + name + '\'' +
                ", productId='" + productId + '\'' +
                ", startDate=" + startDate +
                ", subscriberId='" + subscriberId + '\'' +
                ", type='" + type + '\'' +
                ", upperCondition=" + upperCondition +
                '}';
    }
}
