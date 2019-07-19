package com.hvn.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hvn.spring.dao.GenericDao;
import com.hvn.spring.dto.DomainDTO;
import com.hvn.spring.dto.JsonResponseDTO;
import com.hvn.spring.utils.constants.URLMappingConstants;

@Controller
@RequestMapping(value = URLMappingConstants.API_MAPPING)
public class DomainController {

	@Autowired
	private GenericDao crudService;

	@RequestMapping(value = URLMappingConstants.DOMAIN_SEARCH, method = RequestMethod.GET)
	public @ResponseBody JsonResponseDTO search(
			@RequestParam(value = URLMappingConstants.CRITERIA_PARAM) String criteria) {

		List<DomainDTO> domainDTOs = new ArrayList<DomainDTO>();
		
		if (criteria == null || criteria.isEmpty()) {			
			domainDTOs = crudService.findCountries();			
		} 
		else {		
			domainDTOs = crudService.findProvinces(criteria);
		}
		
		return new JsonResponseDTO(domainDTOs);
	}
}
