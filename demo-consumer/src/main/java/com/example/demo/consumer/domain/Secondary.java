package com.example.demo.consumer.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hungnguyenv3 on 10/16/15.
 */
@Entity
@Table(name = "`secondary`")
public class Secondary implements Serializable {

    private static final long serialVersionUID = -9188909900257549239L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String payload;

    public Secondary() {
    }

    public Secondary(String payload) {
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

        Secondary secondary = (Secondary) o;

        if (id != null ? !id.equals(secondary.id) : secondary.id != null) return false;
        return !(payload != null ? !payload.equals(secondary.payload) : secondary.payload != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Secondary{" +
                "id=" + id +
                ", payload='" + payload + '\'' +
                '}';
    }
}
