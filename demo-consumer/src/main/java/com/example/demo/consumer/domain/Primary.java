package com.example.demo.consumer.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hungnguyenv3 on 10/16/15.
 */
@Entity
@Table(name = "`primary`")
public class Primary implements Serializable {

    private static final long serialVersionUID = -2871802525930667013L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String payload;

    public Primary() {
    }

    public Primary(String payload) {
        this.payload = payload;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        if (id != null ? !id.equals(primary.id) : primary.id != null) return false;
        return !(payload != null ? !payload.equals(primary.payload) : primary.payload != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Primary{" +
                "id=" + id +
                ", payload='" + payload + '\'' +
                '}';
    }
}
