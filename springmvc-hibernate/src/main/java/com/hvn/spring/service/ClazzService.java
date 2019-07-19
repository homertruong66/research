package com.hvn.spring.service;

import com.hvn.spring.dto.ClazzDTO;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.utils.criteria.SearchCriteria;

import java.util.List;


public interface ClazzService {

	public ReturnValue<Long> save(ClazzDTO clazzDTO);
	
    public ReturnValue<List<ClazzDTO>> search(SearchCriteria searchCriteria);

}
