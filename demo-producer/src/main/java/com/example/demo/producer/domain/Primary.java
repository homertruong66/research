package com.example.demo.producer.domain;

import java.io.Serializable;

/**
 * Created by hungnguyenv3 on 10/16/15.
 */
public class Primary implements Serializable {

    private static final long serialVersionUID = -2871802525930667013L;

    private String payload;

    public Primary() {
    }

    public Primary(String payload) {
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

        Primary primary = (Primary) o;

        return !(payload != null ? !payload.equals(primary.payload) : primary.payload != null);

    }

    @Override
    public int hashCode() {
        return payload != null ? payload.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Primary{" +
                "payload='" + payload + '\'' +
                '}';
    }
}
