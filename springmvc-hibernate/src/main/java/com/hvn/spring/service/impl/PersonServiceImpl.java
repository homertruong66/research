package com.hvn.spring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hvn.spring.dao.GenericDao;
import com.hvn.spring.dto.StudentDTO;
import com.hvn.spring.dto.TeacherDTO;
import com.hvn.spring.dto.TeacherTransferEditDTO;
import com.hvn.spring.model.Department;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.model.Student;
import com.hvn.spring.model.Teacher;
import com.hvn.spring.service.PersonService;
import com.hvn.spring.utils.criteria.SearchCriteria;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PersonServiceImpl implements PersonService {

	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	protected HibernateTemplate hibernateTemplate;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public <T> T delete(Class<?> clazz, long id) {
		return genericDao.delete(clazz, id);
	}

	@Override
	public ReturnValue<TeacherDTO> getTeacher(long id) {
		ReturnValue<TeacherDTO> returnValue = new ReturnValue<TeacherDTO>();
		
		TeacherDTO dto = new TeacherDTO();
		
		Teacher model = genericDao.get(Teacher.class, id);
		if (model != null) {
			dto.copyFrom(model);
			returnValue.setData(dto);
		}else{
			returnValue.setError("Teacher is empty");
		}

		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ReturnValue<List<TeacherDTO>> getTeachersByDepartment(long departmentId) {
		List<TeacherDTO> teacherDTOs = new ArrayList<TeacherDTO>();;
		
		ReturnValue<List<TeacherDTO>> returnValue = new ReturnValue<List<TeacherDTO>>();
		
		String queryString = "from Teacher where department.id = ?";
		List<Teacher> teachers = (List<Teacher>) hibernateTemplate.find(queryString, departmentId);
		if (teachers != null && teachers.size() > 0) {
			teachers.stream().forEach(teacher -> {
				TeacherDTO teacherDTO = new TeacherDTO();
				teacherDTO.copyFrom(teacher);
				
				teacherDTOs.add(teacherDTO);
			});
			returnValue.setData(teacherDTOs);
		}else{
			returnValue.setError("Teachers is empty");
		}
		
		return returnValue;
	}
	
	@Override
	public ReturnValue<StudentDTO> getStudent(long id) {
		Student model = genericDao.get(Student.class, id);
		
		ReturnValue<StudentDTO> returnValue = new ReturnValue<StudentDTO>();
		
		StudentDTO dto = new StudentDTO();
		if (model != null) {
			dto.copyFrom(model);
			returnValue.setData(dto);
		}else{
			returnValue.setError("Student is empty");
		}

		return returnValue;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ReturnValue<Long> saveTeacher(TeacherDTO dto) {
		Teacher model = genericDao.get(Teacher.class, dto.getId());
		ReturnValue<Long> returnValue = new ReturnValue<Long>();
		try {
			if (model == null) {
				model = new Teacher();
			}
			
			// backup department
			Department departmentBK = model.getDepartment();
			
			// setting new attribute
			dto.copyTo(model);
			if (dto.getDepartment() != null) {
				Department department = genericDao.get(Department.class, dto.getDepartment().getId());
				model.setDepartment(department);
			} else {
				model.setDepartment(departmentBK);
			}
			
			Long result = genericDao.save(model);
			returnValue.setData(result);
		} catch (Exception e) {
			returnValue.setError(e.getMessage());
		}
		return returnValue;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ReturnValue<Long> saveStudent(StudentDTO dto) {
		ReturnValue<Long> returnValue = new ReturnValue<Long>();
		try {
			Student model = genericDao.get(Student.class, dto.getId());
			if (model == null) {
				model = new Student();
			}
			
			dto.copyTo(model);

			Long result = genericDao.save(model);
			returnValue.setData(result);
		} catch (Exception e) {
			returnValue.setError(e.getMessage());
		}
		
		return returnValue;
	}

	@Override
	public ReturnValue<List<TeacherDTO>> searchTeachers(SearchCriteria searchCriteria) {
		List<Teacher> models = genericDao.find(searchCriteria, Teacher.class);
		ReturnValue<List<TeacherDTO>> returnValue = new ReturnValue<List<TeacherDTO>>();
		List<TeacherDTO> dtos = new ArrayList<TeacherDTO>();

		if (models != null && models.size() > 0) {

			models.stream().forEach(model -> {

				TeacherDTO dto = new TeacherDTO();
				dto.copyFrom(model);
				
				dtos.add(dto);
			});
			returnValue.setData(dtos);
		}else{
			returnValue.setError("Teachers is empty");
		}

		return returnValue;
	}
	
	@Override
	public ReturnValue<List<StudentDTO>> searchStudents(SearchCriteria searchCriteria) {
		List<Student> models = genericDao.find(searchCriteria, Student.class);
		ReturnValue<List<StudentDTO>> returnValue = new ReturnValue<List<StudentDTO>>();
		List<StudentDTO> dtos = new ArrayList<StudentDTO>();

		if (models != null && models.size() > 0) {

			models.stream().forEach(model -> {

				StudentDTO dto = new StudentDTO();
				dto.copyFrom(model);
				
				dtos.add(dto);
			});
			returnValue.setData(dtos);
		}else{
			returnValue.setError("Students is empty");
		}

		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public <T> T transferTeachers(TeacherTransferEditDTO teacherTransferEditDTO) {		
		ReturnValue<T> returnValue = new ReturnValue<T>(); 
		try {
			Teacher teacherLeft = genericDao.get(Teacher.class,teacherTransferEditDTO.getLeftTeacherId());
			Department departmentLeft = genericDao.get(Department.class,teacherTransferEditDTO.getLeftDepartmentId());
			teacherLeft.setDepartment(departmentLeft);
			genericDao.save(teacherLeft);
			
			Teacher teacherRight = genericDao.get(Teacher.class, teacherTransferEditDTO.getRightTeacherId());
			Department departmentRight = genericDao.get(Department.class,teacherTransferEditDTO.getRightDepartmentId());
			teacherRight.setDepartment(departmentRight);
			genericDao.save(teacherRight);
		} catch (Exception e) {
			returnValue.setError(e.getMessage());
		}
		return (T) returnValue;
	}
}
