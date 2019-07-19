package com.example.demo.producer.domain;

import java.io.Serializable;

/**
 * Created by hungnguyenv3 on 10/16/15.
 */
public class Secondary implements Serializable {

    private static final long serialVersionUID = -9188909900257549239L;

    private String payload;

    public Secondary() {
    }

    public Secondary(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Secondary secondary = (Secondary) o;

        return !(payload != null ? !payload.equals(secondary.payload) : secondary.payload != null);

    }

    @Override
    public int hashCode() {
        return payload != null ? payload.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Secondary{" +
                "payload='" + payload + '\'' +
                '}';
    }
}
