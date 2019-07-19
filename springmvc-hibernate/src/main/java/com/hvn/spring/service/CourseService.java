package com.hvn.spring.service;

import java.util.List;

import com.hvn.spring.dto.CourseDTO;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.utils.criteria.SearchCriteria;


public interface CourseService {

    public ReturnValue<List<CourseDTO>> search(SearchCriteria searchCriteria);

}
