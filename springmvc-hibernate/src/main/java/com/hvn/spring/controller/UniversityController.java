package com.hvn.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.hvn.spring.dto.JsonResponseDTO;
import com.hvn.spring.dto.UniversityDTO;
import com.hvn.spring.dto.UniversityListDTO;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.service.UniversityService;
import com.hvn.spring.utils.HVNSpringUtils;
import com.hvn.spring.utils.constants.URLMappingConstants;
import com.hvn.spring.utils.criteria.SearchCriteria;

@Controller
@RequestMapping(value = URLMappingConstants.API_MAPPING)
public class UniversityController {

	@Autowired
	Gson gson;
	
	@Autowired
	private UniversityService universityService;
	
	@RequestMapping(value = URLMappingConstants.UNIVERSITY_DELETE, method = RequestMethod.POST)
	public @ResponseBody JsonResponseDTO delete(@RequestParam(value = URLMappingConstants.ID_PARAM) long id) {
		ReturnValue<String> returnValue = universityService.delete(id);
		
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		if(!returnValue.getError().equals("")){
			jsonResponseDTO.setError(returnValue.getError());
		}else{
			jsonResponseDTO.setData(returnValue.getData());
		}
		
		return jsonResponseDTO;
	}
	
	@RequestMapping(value = URLMappingConstants.UNIVERSITY_GET_TO_EDIT, method = RequestMethod.GET)
	public @ResponseBody JsonResponseDTO getToEdit(@RequestParam(value = URLMappingConstants.ID_PARAM) long id){
		ReturnValue<UniversityDTO> returnValue = universityService.get(id);
		
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		if(!returnValue.getError().equals("")){
			jsonResponseDTO.setError(returnValue.getError());
		}else{
			jsonResponseDTO.setData(returnValue.getData());
		}
		
		return jsonResponseDTO;
	}
	
	@RequestMapping(value = URLMappingConstants.UNIVERSITY_SAVE, method = RequestMethod.POST)
	public @ResponseBody JsonResponseDTO save(@RequestParam(value = URLMappingConstants.ENTITY_PARAM) String entiryStr){
		
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		UniversityDTO universityDTO = new UniversityDTO();
		if(entiryStr != null && !entiryStr.isEmpty()){
			universityDTO = gson.fromJson(entiryStr, UniversityDTO.class);
			ReturnValue<Long> returnValue = universityService.save(universityDTO);
			if(!returnValue.getError().equals("")){
				jsonResponseDTO.setError(returnValue.getError());
			}else{
				jsonResponseDTO.setData(returnValue.getData());
			}
		}
		
		return jsonResponseDTO;
	}
	
	@RequestMapping(value = URLMappingConstants.UNIVERSITY_SEARCH, method = RequestMethod.GET)
	public @ResponseBody JsonResponseDTO search(@RequestParam(value = URLMappingConstants.CRITERIA_PARAM) String criteriaStr){		
		SearchCriteria searchCriteria = HVNSpringUtils.createSearchCriteria(criteriaStr);
		
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		
		ReturnValue<List<UniversityDTO>> returnValue = universityService.search(searchCriteria); 
		if(!returnValue.getError().equals("")){
			jsonResponseDTO.setError(returnValue.getError());			
		}else{
			List<UniversityDTO> universityDTOs = returnValue.getData();			
			// create TeacherListDTO
			UniversityListDTO universityListDTO = new UniversityListDTO();
			universityListDTO.getUniversityEditModels().addAll(universityDTOs);
			
			// get total result
			if (universityDTOs != null && universityDTOs.size() > 0) {
				searchCriteria.removePagingInfo();
				returnValue = universityService.search(searchCriteria); 
				List<UniversityDTO> totalUniversityDTOs = returnValue.getData();
				
				universityListDTO.setTotalResult(totalUniversityDTOs.size());
			}
			jsonResponseDTO.setData(universityListDTO);
		}

		return jsonResponseDTO;
	}
}
