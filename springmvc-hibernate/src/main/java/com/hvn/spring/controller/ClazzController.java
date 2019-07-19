package com.hvn.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.hvn.spring.dto.ClazzDTO;
import com.hvn.spring.dto.JsonResponseDTO;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.service.ClazzService;
import com.hvn.spring.utils.HVNSpringUtils;
import com.hvn.spring.utils.constants.URLMappingConstants;
import com.hvn.spring.utils.criteria.SearchCriteria;

/**
 * Created by vietnguyenq1 on 2/5/2015.
 */

@Controller
@RequestMapping(value = URLMappingConstants.API_MAPPING)
public class ClazzController {

	@Autowired
	private ClazzService clazzService;
	
	@Autowired
	Gson gson;

	@RequestMapping(value = URLMappingConstants.CLAZZ_SAVE, method = RequestMethod.POST)
	public @ResponseBody JsonResponseDTO save(@RequestParam(value = URLMappingConstants.ENTITY_PARAM) String entiryStr) {		
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		if (entiryStr != null && !entiryStr.isEmpty()) {
			ClazzDTO clazzDTO = gson.fromJson(entiryStr, ClazzDTO.class);
			ReturnValue<Long> returnValue = clazzService.save(clazzDTO);
			if(!returnValue.getError().equals("")){
				jsonResponseDTO.setError(returnValue.getError());
			}else{
				jsonResponseDTO.setData(returnValue.getData());
			}
		}
		
		return jsonResponseDTO;
	}
	
	@RequestMapping(value = URLMappingConstants.CLAZZ_SEARCH, method = RequestMethod.GET)
	public @ResponseBody JsonResponseDTO search(@RequestParam(value = URLMappingConstants.CRITERIA_PARAM) String criteriaStr){
		SearchCriteria searchCriteria = HVNSpringUtils.createSearchCriteria(criteriaStr);
		ReturnValue<List<ClazzDTO>> returnValue = clazzService.search(searchCriteria);
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		if(!returnValue.getError().equals("")){
			jsonResponseDTO.setError(returnValue.getError());
		}else{
			jsonResponseDTO.setData(returnValue.getData());
		}
		return jsonResponseDTO;
	}
}
