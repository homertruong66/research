package com.hoang.app.dto;

import com.hoang.app.domain.Address;
import com.hoang.app.domain.Organization;

/**
 *
 * @author htruong
 */

public class OrganizationDTO {
	private Long id;
    private String name;    
    private Address address;
    private String email;
    private Integer version;

    public void copyFromOrganization(Organization org) {
    	setId(org.getId());
		setName(org.getName());		
		setAddress(org.getAddress());		
		setEmail(org.getEmail());
        setVersion(org.getVersion());       	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


    
}
