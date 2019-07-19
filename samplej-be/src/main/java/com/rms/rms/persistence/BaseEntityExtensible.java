package com.rms.rms.persistence;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * homertruong
 */

public abstract class BaseEntityExtensible extends BaseEntity {

    protected String PROPERTY_ENTITY_NAME = null;

    public boolean isPropNotExisted() {
        if (StringUtils.isBlank(PROPERTY_ENTITY_NAME)) {
            return true;
        }
        return false;
    }

    private CustomPropertyMap customPropertyMap = new CustomPropertyMap();

    public CustomPropertyMap getCustomPropertyMap() {
        return customPropertyMap;
    }

    public void setCustomPropertyMap(CustomPropertyMap customPropertyMap) {
        if (customPropertyMap != null) {
            this.customPropertyMap.putAll(customPropertyMap);
        } else {
            this.customPropertyMap.clear();
        }
    }

    public void setCustomPropertyMap(Map<String, Object> props) {
        if (props != null) {
            customPropertyMap.putAll(props);
        } else {
            customPropertyMap.clear();
        }
    }

    public void setValue(String property, Object value) {
        customPropertyMap.setValue(property, value);
    }

    public void deleteValue(String property) {
        customPropertyMap.put(property, null);
    }

    public String getStringValue(String property) {
        return customPropertyMap.getStringValue(property);
    }

    public <T> T getValue(String property) {
        return customPropertyMap.getValue(property);
    }

    public int getIntValue(String property) {
        return customPropertyMap.getIntValue(property);
    }

    public long getLongValue(String property) {
        return customPropertyMap.getLongValue(property);
    }

    public float getFloatValue(String property) {
        return customPropertyMap.getFloatValue(property);
    }

    public double getDoubleValue(String property) {
        return customPropertyMap.getDoubleValue(property);
    }

    public Date getDateValue(String property) {
        return customPropertyMap.getDateValue(property);
    }

    public List<?> getListValue(String property) {
        return customPropertyMap.getListValue(property);
    }

    public Map<?, ?> getMapValue(String property) {
        return customPropertyMap.getMapValue(property);
    }

    public boolean hasProperty(String property) {
        return customPropertyMap.containsKey(property);
    }

}
