package com.hoang.restconsume.dto;

import java.io.Serializable;

public class DepartmentDto implements Serializable {

    private static final long serialVersionUID = 864026381198649046L;

    private String id;

    private String name;

    private int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "DepartmentDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}