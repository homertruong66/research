package com.hvn.spring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hvn.spring.dao.GenericDao;
import com.hvn.spring.dto.ClazzDTO;
import com.hvn.spring.model.Clazz;
import com.hvn.spring.model.Course;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.model.Teacher;
import com.hvn.spring.service.ClazzService;
import com.hvn.spring.utils.criteria.SearchCriteria;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private GenericDao genericDao;

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    @Override
    public ReturnValue<Long> save(ClazzDTO clazzDTO) {
    	ReturnValue<Long> returnValue = new ReturnValue<Long>();
    	try {
    		Clazz clazz =  new Clazz();
            clazzDTO.copyTo(clazz);
            
            Course course = genericDao.get(Course.class,clazzDTO.getCourse().getId());
            if(course!=null){
                clazz.setCourse(course);
            }
            
            Teacher teacher = genericDao.get(Teacher.class,clazzDTO.getTeacher().getId());
            if(teacher!= null){
                clazz.setCourse(course);
            }
            
            long classId = genericDao.save(clazz);
            returnValue.setData(classId);
		} catch (Exception e) {
			returnValue.setError(e.getMessage());
		}
    	
        return returnValue;
    }
    
    @Override
    public ReturnValue<List<ClazzDTO>> search(SearchCriteria searchCriteria) {
        List<Clazz> clazzs = genericDao.find(searchCriteria, Clazz.class);
        List<ClazzDTO> clazzDTOs = new ArrayList<ClazzDTO>(0);
        ReturnValue<List<ClazzDTO>> returnValue = new ReturnValue<List<ClazzDTO>>();
        try {
        	if (clazzs != null && clazzs.size() > 0) {
                clazzs.forEach(clazz -> {
                    ClazzDTO clazzDTO = new ClazzDTO();
                    clazzDTO.copyFrom(clazz);
                    clazzDTOs.add(clazzDTO);
                });
                returnValue.setData(clazzDTOs);
            }
		} catch (Exception e) {
			returnValue.setError(e.getMessage());
		}
        
        return returnValue;
    }
}
