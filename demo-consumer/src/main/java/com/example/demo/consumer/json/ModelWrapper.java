package com.example.demo.consumer.json;

import com.example.demo.consumer.domain.Primary;
import com.example.demo.consumer.domain.Secondary;

import java.io.Serializable;

/**
 * Created by hungnguyenv3 on 10/19/15.
 */
public class ModelWrapper implements Serializable {

    private static final long serialVersionUID = 6566611405373970418L;

    private Primary primary;

    private Secondary secondary;

    public ModelWrapper() {
    }

    public ModelWrapper(Primary primary, Secondary secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    public Primary getPrimary() {
        return primary;
    }

    public void setPrimary(Primary primary) {
        this.primary = primary;
    }

    public Secondary getSecondary() {
        return secondary;
    }

    public void setSecondary(Secondary secondary) {
        this.secondary = secondary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelWrapper that = (ModelWrapper) o;

        if (primary != null ? !primary.equals(that.primary) : that.primary != null) return false;
        return !(secondary != null ? !secondary.equals(that.secondary) : that.secondary != null);

    }

    @Override
    public int hashCode() {
        int result = primary != null ? primary.hashCode() : 0;
        result = 31 * result + (secondary != null ? secondary.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ModelWrapper{" +
                "primary=" + primary +
                ", secondary=" + secondary +
                '}';
    }
}
