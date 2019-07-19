package com.hoang.srj.view_model;

import com.hoang.srj.model.University;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UniversityCreateModel implements Serializable {

    private static final long serialVersionUID = -5108097020954238070L;

    @NotNull(message = "'name' can't be null")
    private String name;

    public void copyTo (University university) {
        Assert.notNull(university);
        university.setName(this.getName());
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UniversityCreateModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
