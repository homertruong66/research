package com.hvn.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hvn.spring.dao.GenericDao;
import com.hvn.spring.dto.DepartmentDTO;
import com.hvn.spring.dto.UniversityDTO;
import com.hvn.spring.model.Department;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.model.University;
import com.hvn.spring.service.UniversityService;
import com.hvn.spring.utils.criteria.SearchCriteria;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UniversityServiceImpl implements UniversityService{

	@Autowired
	private GenericDao genericDao;		

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public <T> T delete(long id) {
		return genericDao.delete(University.class, id);
	}
	
	@Override
	public ReturnValue<UniversityDTO> get(long id) {		
		ReturnValue<UniversityDTO> returnValue = new ReturnValue<UniversityDTO>();
		
		University university = genericDao.get(University.class, id);
		UniversityDTO universityDTO = new UniversityDTO();
		
		if(university != null){
			universityDTO.copyFrom(university);
			returnValue.setData(universityDTO);
		}else{
			returnValue.setError("University not found");
		}
		
		return returnValue;
	}
	
	@Override
	public ReturnValue<List<DepartmentDTO>> getDepartments() {		
		ReturnValue<List<DepartmentDTO>> returnValue = new ReturnValue<List<DepartmentDTO>>();		
		List<DepartmentDTO> departmentDTOs = new ArrayList<DepartmentDTO>();
		
		List<Department> departments = genericDao.getAll(Department.class);
		if (departments != null) {
			departments.stream().forEach(department -> {
				DepartmentDTO departmentDTO = new DepartmentDTO();
				departmentDTO.copyFrom(department);				
				departmentDTOs.add(departmentDTO);
			});			
			returnValue.setData(departmentDTOs);
		}else{
			returnValue.setError("Department is empty");
		}
		
		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ReturnValue<List<DepartmentDTO>> getDepartments(long universityId) {		
		ReturnValue<List<DepartmentDTO>> returnValue = new ReturnValue<List<DepartmentDTO>>();		
		List<DepartmentDTO> departmentDTOs = new ArrayList<DepartmentDTO>();
		
		University university = genericDao.get(University.class, universityId);
		if (university != null) {
			Set<Department> departments = university.getDepartments();
			departments.stream().forEach(department -> {
				DepartmentDTO departmentDTO = new DepartmentDTO();
				departmentDTO.copyFrom(department);				
				departmentDTOs.add(departmentDTO);
			});			
			returnValue.setData(departmentDTOs);
		}else{
			returnValue.setError("University is empty");
		}
		
		return returnValue;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ReturnValue<Long> save(UniversityDTO universityDTO) {		
		ReturnValue<Long> returnValue = new ReturnValue<Long>();
		try {
			
			University university = genericDao.get(University.class, universityDTO.getId());
			if (university == null) {
				university = new University();
			}
			
			universityDTO.copyTo(university);
			long universityId = genericDao.save(university);
			returnValue.setData(universityId);
			
		} catch (Exception e) {
			returnValue.setError(e.getMessage());
		}
		
		return returnValue;
	}

	@Override
	public ReturnValue<List<UniversityDTO>> search(SearchCriteria searchCriteria) {
		List<University> universities = genericDao.find(searchCriteria, University.class);
		ReturnValue<List<UniversityDTO>> returnValue = new ReturnValue<List<UniversityDTO>>();
		List<UniversityDTO> universityDTOs = new ArrayList<UniversityDTO>();

		if (universities != null && universities.size() > 0) {
			universities.stream().forEach(university -> {

				UniversityDTO universityDTO = new UniversityDTO();
				universityDTO.copyFrom(university);				
				universityDTOs.add(universityDTO);
			});			
			returnValue.setData(universityDTOs);
		}else{
			returnValue.setError("Univesity is empty");
		}
		
		return returnValue;
	}
}
