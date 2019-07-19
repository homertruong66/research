package com.hvn.spring.dto;

import java.util.ArrayList;
import java.util.List;

public class UniversityListDTO {

	private long totalResult;
	
	private List<UniversityDTO> universityEditModels = new ArrayList<UniversityDTO>();

	public List<UniversityDTO> getUniversityEditModels() {
		return universityEditModels;
	}

	public void setUniversityEditModels(List<UniversityDTO> universityEditModels) {
		this.universityEditModels = universityEditModels;
	}

	public long getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(long totalResult) {
		this.totalResult = totalResult;
	}
	
}
