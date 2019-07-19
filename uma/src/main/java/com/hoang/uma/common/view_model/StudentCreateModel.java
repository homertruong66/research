package com.hoang.uma.common.view_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * homertruong
 */

public class StudentCreateModel extends UserCreateModel implements Serializable {

    private static final long serialVersionUID = -156992338157821582L;

    @JsonProperty
    @Min(value = 1, message = "'Year' must be greater than 0!")
    @Max(value = 5, message = "'Year' must be less than 6!")
    private int year = 1;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "StudentCreateModel{" +
                ", year=" + year +
                "} - " + super.toString();
    }
}
