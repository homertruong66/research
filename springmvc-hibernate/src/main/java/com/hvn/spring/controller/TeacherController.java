package com.hvn.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.hvn.spring.dto.DepartmentDTO;
import com.hvn.spring.dto.JsonResponseDTO;
import com.hvn.spring.dto.TeacherDTO;
import com.hvn.spring.dto.TeacherEditDTO;
import com.hvn.spring.dto.TeacherListDTO;
import com.hvn.spring.dto.TeacherTransferEditDTO;
import com.hvn.spring.dto.TeacherTransferListDTO;
import com.hvn.spring.dto.UniversityDTO;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.model.Teacher;
import com.hvn.spring.service.PersonService;
import com.hvn.spring.service.UniversityService;
import com.hvn.spring.utils.Constants;
import com.hvn.spring.utils.HVNSpringUtils;
import com.hvn.spring.utils.constants.URLMappingConstants;
import com.hvn.spring.utils.criteria.SearchCriteria;

@Controller
@RequestMapping(value = URLMappingConstants.API_MAPPING)
public class TeacherController {

	@Autowired
	private Gson gson;
	
	@Autowired
	private PersonService personService;

	@Autowired
	private UniversityService universityService;

	@RequestMapping(value = URLMappingConstants.TEACHER_DELETE, method = RequestMethod.POST)
	public @ResponseBody JsonResponseDTO delete(
			@RequestParam(value = URLMappingConstants.ID_PARAM) long id) {

		personService.delete(Teacher.class, id);

		return new JsonResponseDTO();
	}

	@RequestMapping(value = URLMappingConstants.TEACHER_GET_TO_EDIT, method = RequestMethod.GET)
	public @ResponseBody JsonResponseDTO getToEdit(
			@RequestParam(value = URLMappingConstants.ID_PARAM) long id) {
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		TeacherEditDTO teacherEditDTO = new TeacherEditDTO();
		
		// Get Department
		List<DepartmentDTO> departmentDTOs = new ArrayList<DepartmentDTO>();
		ReturnValue<List<DepartmentDTO>> departmentReturnValue = universityService.getDepartments();
		if(departmentReturnValue.getError().equals("")){
			departmentDTOs.addAll(departmentReturnValue.getData());			
		}
		teacherEditDTO.setDepartments(departmentDTOs);
				
		// Get Teacher
		if (id != Constants.DEFAULT_ID) {
			ReturnValue<TeacherDTO> returnValue = personService.getTeacher(id);
			if (!returnValue.getError().equals("")){
				jsonResponseDTO.setError(returnValue.getError());
			} else {
				teacherEditDTO.setTeacher(returnValue.getData());
			}
		}
		
		jsonResponseDTO.setData(teacherEditDTO);
		return jsonResponseDTO;
	}

	@RequestMapping(value = URLMappingConstants.TEACHER_GET_TO_TRANSFER, method = RequestMethod.GET)
	public @ResponseBody JsonResponseDTO getToTransfer(
			@RequestParam(value = URLMappingConstants.ENTITY_PARAM) String entiryStr) {

		TeacherTransferListDTO teacherTransferListDTO = new TeacherTransferListDTO();
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		
		if (entiryStr != null && !entiryStr.isEmpty()) {
			TeacherTransferEditDTO teacherTransferEditDTO = gson.fromJson(
					entiryStr, TeacherTransferEditDTO.class);

			// get Universities
			List<UniversityDTO> universityDTOs = universityService.search(new SearchCriteria()).getData();
			teacherTransferListDTO.setUniversities(universityDTOs);

			// get Departments
			if (teacherTransferEditDTO.getLeftUniversityId() != Constants.DEFAULT_ID) {
				List<DepartmentDTO> leftDepartments = universityService.getDepartments(teacherTransferEditDTO.getLeftUniversityId()).getData();
				teacherTransferListDTO.setLeftDepartments(leftDepartments);
			}

			if (teacherTransferEditDTO.getRightUniversityId() != Constants.DEFAULT_ID) {
				List<DepartmentDTO> rightDepartments = universityService
						.getDepartments(teacherTransferEditDTO
								.getRightUniversityId()).getData();
				teacherTransferListDTO.setRightDepartments(rightDepartments);
			}

			// get Teachers
			if (teacherTransferEditDTO.getLeftDepartmentId() != Constants.DEFAULT_ID) {
				ReturnValue<List<TeacherDTO>> leftTeachers = personService.getTeachersByDepartment(teacherTransferEditDTO.getLeftDepartmentId());
				if(!leftTeachers.getError().equals("")){
					jsonResponseDTO.setError(leftTeachers.getError());
				}else{
					teacherTransferListDTO.setLeftTeachers(leftTeachers.getData());
					jsonResponseDTO.setData(teacherTransferListDTO);
				}
			}

			if (teacherTransferEditDTO.getRightDepartmentId() != Constants.DEFAULT_ID) {
				ReturnValue<List<TeacherDTO>> rightTeachers = personService.getTeachersByDepartment(teacherTransferEditDTO.getRightDepartmentId());
				if(!rightTeachers.getError().equals("")){
					jsonResponseDTO.setError(rightTeachers.getError());
				}else{
					teacherTransferListDTO.setLeftTeachers(rightTeachers.getData());
					jsonResponseDTO.setData(teacherTransferListDTO);
				}
			}
		}
		
		return jsonResponseDTO;
	}
	
	@RequestMapping(value = URLMappingConstants.TEACHER_SAVE, method = RequestMethod.POST)
	public @ResponseBody JsonResponseDTO save(
			@RequestParam(value = URLMappingConstants.ENTITY_PARAM) String entiryStr) {
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		if (entiryStr != null && !entiryStr.isEmpty()) {
			TeacherDTO teacherDTO = gson.fromJson(entiryStr, TeacherDTO.class);
			ReturnValue<Long> returnValue = personService.saveTeacher(teacherDTO);
			if(!returnValue.getError().equals("")){
				jsonResponseDTO.setError(returnValue.getError());
			}else{
				jsonResponseDTO.setData(returnValue.getData());
			}
		}

		return jsonResponseDTO;
	}

	@RequestMapping(value = URLMappingConstants.TEACHER_SEARCH, method = RequestMethod.GET)
	public @ResponseBody JsonResponseDTO search(
			@RequestParam(value = URLMappingConstants.CRITERIA_PARAM) String criteriaStr) {
		
		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		SearchCriteria searchCriteria = HVNSpringUtils.createSearchCriteria(criteriaStr);
		
		ReturnValue<List<TeacherDTO>> returnValue = personService.searchTeachers(searchCriteria);
		if(!returnValue.getError().equals("")){
			jsonResponseDTO.setError(returnValue.getError());
		}else{
			// create TeacherListDTO
			TeacherListDTO teacherListDTO = new TeacherListDTO();
			teacherListDTO.getTeachers().addAll(returnValue.getData());

			// get total result
			if (returnValue != null && returnValue.getData().size() > 0) {
				searchCriteria.removePagingInfo();
				returnValue = personService.searchTeachers(searchCriteria);
				List<TeacherDTO> totalTeacherDTOs = returnValue.getData();

				teacherListDTO.setTotalResult(totalTeacherDTOs.size());
			}
			jsonResponseDTO.setData(teacherListDTO);
		}

		return jsonResponseDTO;
	}

	@RequestMapping(value = URLMappingConstants.TEACHER_TRANSFER, method = RequestMethod.POST)
	public @ResponseBody JsonResponseDTO transfer(
			@RequestParam(value = URLMappingConstants.ENTITY_PARAM) String entiryStr) {

		JsonResponseDTO jsonResponseDTO = new JsonResponseDTO();
		
		if (entiryStr != null && !entiryStr.isEmpty()) {
			TeacherTransferEditDTO teacherTransferEditDTO = gson.fromJson(
					entiryStr, TeacherTransferEditDTO.class);

			ReturnValue<?> returnValue = personService.transferTeachers(teacherTransferEditDTO);
			if(!returnValue.getError().equals("")){
				jsonResponseDTO.setError(returnValue.getError());
			}
		}

		return jsonResponseDTO;
	}
}
