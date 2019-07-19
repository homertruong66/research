package com.hoang.module.mail.dto;

import com.hoang.module.mail.domain.Item;

/**
 *
 * @author htruong
 */

public class ItemDTO {
	private Long id;
    private String name;
    private String description;
    private String dateCreated;
    private String dateModified;
    private Long mailerId;
    private Integer version;

    public void copyFromItem(Item item) {
    	this.setId(item.getId());
        this.setName(item.getName());
        this.setDescription(item.getDescription());
        this.setDateCreated(item.getDateCreated());
        this.setDateModified(item.getDateModified());
        this.setVersion(item.getVersion());
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
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
