package com.hvn.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hvn.spring.dto.CourseDTO;
import com.hvn.spring.dto.JsonResponseDTO;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.service.CourseService;
import com.hvn.spring.utils.HVNSpringUtils;
import com.hvn.spring.utils.constants.URLMappingConstants;
import com.hvn.spring.utils.criteria.SearchCriteria;

/**
 * Created by vietnguyenq1 on 2/5/2015.
 */

@Controller
@RequestMapping(value = URLMappingConstants.API_MAPPING)
public class CourseController {
    
	@Autowired
    private CourseService courseService;
    
	@RequestMapping(value = URLMappingConstants.COURSE_GET_TO_EDIT)
	public @ResponseBody JsonResponseDTO getToEdit(@RequestParam(value = URLMappingConstants.CRITERIA_PARAM) String criteriaStr){
        SearchCriteria searchCriteria = HVNSpringUtils.createSearchCriteria(criteriaStr);
        
        ReturnValue<List<CourseDTO>> returnValue = courseService.search(searchCriteria);
        JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
        if(!returnValue.getError().equals("")){
        	jsonResponseDTO.setError(returnValue.getError());
        }else{
        	jsonResponseDTO.setData(returnValue.getData());
        }
        
        return jsonResponseDTO;
	}
}
