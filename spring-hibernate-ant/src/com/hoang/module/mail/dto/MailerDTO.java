package com.hoang.module.mail.dto;

import com.hoang.module.mail.domain.Mailer;

/**
 *
 * @author htruong
 */

public class MailerDTO {
	private Long id;
    private Integer version;

    public void copyFromMailer(Mailer mailer) {
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
