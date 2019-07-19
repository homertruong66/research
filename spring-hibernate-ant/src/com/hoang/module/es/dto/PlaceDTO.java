package com.hoang.module.es.dto;

import com.hoang.module.es.domain.Place;

/**
 *
 * @author htruong
 */

public class PlaceDTO {
	private Long id;
    private Integer version;

    public void copyFromPlace(Place place) {
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
