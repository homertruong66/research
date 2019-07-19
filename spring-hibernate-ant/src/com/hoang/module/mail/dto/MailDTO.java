package com.hoang.module.mail.dto;

import com.hoang.module.mail.domain.Mail;

/**
 *
 * @author htruong
 */

public class MailDTO {
	private Long id;
    private Integer version;

    public void copyFromMail(Mail mail) {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
    
}
