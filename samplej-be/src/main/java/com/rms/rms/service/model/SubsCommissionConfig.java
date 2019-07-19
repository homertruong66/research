package com.rms.rms.service.model;

import com.rms.rms.persistence.BaseEntityExtensible;

import java.util.HashSet;
import java.util.Set;

/**
 * homertruong
 */

public class SubsCommissionConfig extends BaseEntityExtensible {

    public static final String TYPE_CFE   = "CFE";  // determine seller
    private static final String NAME_CFE   = "Commission Forever";

    public static final String TYPE_COPS  = "COPS";
    private static final String NAME_COPS  = "Commission On Product Set";

    public static final String TYPE_COPPR = "COPPR";
    private static final String NAME_COPPR = "Commission On Product Price Range";

    public static final String TYPE_COPQ = "COPQ";
    private static final String NAME_COPQ = "Commission On Product Quantity";

    public static final String TYPE_CAD   = "CAD";
    private static final String NAME_CAD   = "Commission As Default";

    public static final String TYPE_COAN  = "COAN";
    private static final String NAME_COAN  = "Commission On Affiliate Network";

    public static final String TYPE_COSAL = "COSAL";
    private static final String NAME_COSAL = "Commission On Same Affiliate Level";

    public static final String TYPE_COOV  = "COOV";
    private static final String NAME_COOV  = "Commission On Order Value";

    public static final String TYPE_COPG  = "COPG";
    private static final String NAME_COPG  = "Commission On Priority Group";

    public static final String TYPE_COASV = "COASV";
    private static final String NAME_COASV = "Commission On Affiliate Sales Value";

    private Double commission;
    private Set<ConditionCommissionConfig> conditionCommissionConfigs = new HashSet<>();    // for COPPR, COPQ, COOV, COASV
    private Boolean isEnabled;
    private Set<LayerCommissionConfig> layerCommissionConfigs = new HashSet<>();    // for COAN
    private String name;
    private Set<PriorityGroup> priorityGroups = new HashSet<>();    // for COPG
    private Set<ProductSet> productSets = new HashSet<>();    // for COPS
    private String subscriberId;
    private String type;

    public void addPriorityGroup (PriorityGroup priorityGroup) {
        this.priorityGroups.add(priorityGroup);
    }

    public void addProductSet (ProductSet productSet) {
        this.productSets.add(productSet);
    }

    public Double getCommission () {
        return commission;
    }

    public void setCommission (Double commission) {
        this.commission = commission;
    }

    public Set<ConditionCommissionConfig> getConditionCommissionConfigs() {
        return conditionCommissionConfigs;
    }

    public void setConditionCommissionConfigs(Set<ConditionCommissionConfig> conditionCommissionConfigs) {
        this.conditionCommissionConfigs = conditionCommissionConfigs;
    }

    public Boolean getIsEnabled () {
        return isEnabled;
    }

    public void setIsEnabled (Boolean enabled) {
        isEnabled = enabled;
    }

    public Set<LayerCommissionConfig> getLayerCommissionConfigs () {
        return layerCommissionConfigs;
    }

    public void setLayerCommissionConfigs (Set<LayerCommissionConfig> layerCommissionConfigs) {
        this.layerCommissionConfigs = layerCommissionConfigs;
    }

    public String getName () {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PriorityGroup> getPriorityGroups () {
        return priorityGroups;
    }

    public void setPriorityGroups (Set<PriorityGroup> priorityGroups) {
        this.priorityGroups = priorityGroups;
    }

    public Set<ProductSet> getProductSets() {
        return productSets;
    }

    public void setProductSets(Set<ProductSet> productSets) {
        this.productSets = productSets;
    }

    public String getSubscriberId () {
        return subscriberId;
    }

    public void setSubscriberId (String subscriberId) {
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

    @Override
    public String toString () {
        return "SubsCommissionConfig{" +
               "commission=" + commission +
               ", isEnabled=" + isEnabled +
               ", name='" + name + '\'' +
               ", subscriberId='" + subscriberId + '\'' +
               ", type='" + type + '\'' +
               '}';
    }
}
