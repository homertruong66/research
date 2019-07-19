package com.hvn.spring.service;

import java.util.List;

import com.hvn.spring.dto.DepartmentDTO;
import com.hvn.spring.dto.UniversityDTO;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.utils.criteria.SearchCriteria;

public interface UniversityService {
	
	public <T> T delete(long id);
	
	public ReturnValue<UniversityDTO> get(long id);
	
	public ReturnValue<List<DepartmentDTO>> getDepartments();
	
	public ReturnValue<List<DepartmentDTO>> getDepartments(long universityId);
	
	public ReturnValue<Long> save(UniversityDTO universityDTO);
	
	public ReturnValue<List<UniversityDTO>> search(SearchCriteria searchCriteria);
}
