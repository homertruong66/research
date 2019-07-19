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
import com.hvn.spring.dto.StudentDTO;
import com.hvn.spring.dto.StudentListDTO;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.model.Student;
import com.hvn.spring.service.PersonService;
import com.hvn.spring.utils.HVNSpringUtils;
import com.hvn.spring.utils.constants.URLMappingConstants;
import com.hvn.spring.utils.criteria.SearchCriteria;

@Controller
@RequestMapping(value = URLMappingConstants.API_MAPPING)
public class StudentController {

	@Autowired
	private Gson gson;
	
	@Autowired
	private PersonService studentService;
	
	@RequestMapping(value = URLMappingConstants.STUDENT_DELETE, method = RequestMethod.POST)
	public @ResponseBody JsonResponseDTO delete(
			@RequestParam(value = URLMappingConstants.ID_PARAM) long id) {
		studentService.delete(Student.class, id);
		
		return new JsonResponseDTO();
	}

	@RequestMapping(value = URLMappingConstants.STUDENT_GET_TO_EDIT, method = RequestMethod.GET)
	public @ResponseBody JsonResponseDTO getToEdit(
			@RequestParam(value = URLMappingConstants.ID_PARAM) long id) {
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		
		ReturnValue<StudentDTO> returnValue = studentService.getStudent(id);
		if(!returnValue.getError().equals("")){
			jsonResponseDTO.setError(returnValue.getError());
		}else{
			jsonResponseDTO.setData(returnValue.getData());
		}

		return jsonResponseDTO;
	}
	
	@RequestMapping(value = URLMappingConstants.STUDENT_SAVE, method = RequestMethod.POST)
	public @ResponseBody JsonResponseDTO save(
			@RequestParam(value = URLMappingConstants.ENTITY_PARAM) String entiryStr) {
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		if (entiryStr != null && !entiryStr.isEmpty()) {
			StudentDTO studentDTO = gson.fromJson(entiryStr, StudentDTO.class);			
			ReturnValue<Long> returnValue = studentService.saveStudent(studentDTO);
			if(!returnValue.getError().equals("")){
				jsonResponseDTO.setError(returnValue.getError());
			}else{
				jsonResponseDTO.setData(returnValue.getData());
			}
		}
		
		return jsonResponseDTO;
	}
	
	@RequestMapping(value = URLMappingConstants.STUDENT_SEARCH, method = RequestMethod.GET)
	public @ResponseBody JsonResponseDTO search(
			@RequestParam(value = URLMappingConstants.CRITERIA_PARAM) String criteriaStr) {
		SearchCriteria searchCriteria = HVNSpringUtils.createSearchCriteria(criteriaStr);
		ReturnValue<List<StudentDTO>> returnValue = studentService.searchStudents(searchCriteria);
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		
		if(!returnValue.getError().equals("")){
			jsonResponseDTO.setError(returnValue.getError());
		}else{
			// create StudentListDTO
			StudentListDTO studentListDTO = new StudentListDTO();
			studentListDTO.getStudents().addAll(returnValue.getData());
			
			// get total result
			if (returnValue != null && returnValue.getData().size() > 0) {
				searchCriteria.removePagingInfo();
				returnValue = studentService.searchStudents(searchCriteria);
				List<StudentDTO> totalStudentDTOs = returnValue.getData();
				studentListDTO.setTotalResult(totalStudentDTOs.size());
			}
			
			jsonResponseDTO.setData(studentListDTO);
		}

		return jsonResponseDTO;
	}
}
