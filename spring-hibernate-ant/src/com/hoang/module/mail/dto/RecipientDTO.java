package com.hoang.module.mail.dto;

import com.hoang.module.mail.domain.Recipient;

/**
 *
 * @author htruong
 */

public class RecipientDTO {
	private Long id;
    private String name;
    private String address;
    private Long mailerId;
    private Integer version;

    public void copyFromRecipient(Recipient recipient) {
    	this.setId(recipient.getId());
        this.setName(recipient.getName());
        this.setAddress(recipient.getAddress());
        this.setVersion(recipient.getVersion());
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getMailerId() {
		return mailerId;
	}

	public void setMailerId(Long mailerId) {
		this.mailerId = mailerId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
    
    
}
