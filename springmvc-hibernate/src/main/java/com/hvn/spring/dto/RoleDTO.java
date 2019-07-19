package com.hvn.spring.dto;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by vietnguyenq1 on 1/29/2015.
 */
public class RoleDTO implements GrantedAuthority {
    private Integer version;
    private String name;

    @Override
    public String getAuthority() {
        return this.getName();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
