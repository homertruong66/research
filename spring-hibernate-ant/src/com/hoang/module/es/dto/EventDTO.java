package com.hoang.module.es.dto;

import com.hoang.module.es.domain.Event;

/**
 *
 * @author htruong
 */

public class EventDTO {
	private Long id;
    private Integer version;

    public void copyFromEvent(Event event) {
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
