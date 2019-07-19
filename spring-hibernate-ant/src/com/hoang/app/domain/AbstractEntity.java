package com.hoang.app.domain;

import java.io.Serializable;

/**
 *
 * @author Hoang Truong
 */

public class AbstractEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long transientId = 0l;
    private Long id;
    private String entityType;

    // Constructor
    protected AbstractEntity() {
        entityType = this.getClass().getName();
    }

    @Override
    public String toString() {
    	return entityType + "/" + getId() + "/" + transientId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof AbstractEntity)) {
            return false;
        }

        return obj.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    // getter & setter
    public Long getTransientId() {
        return transientId;
    }

    public void setTransientId(Long transientId) {
        this.transientId = transientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
