package com.hvn.spring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hvn.spring.dao.GenericDao;
import com.hvn.spring.dto.CourseDTO;
import com.hvn.spring.model.Course;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.service.CourseService;
import com.hvn.spring.utils.criteria.SearchCriteria;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CourseServiceImpl implements CourseService {

    @Autowired
    private GenericDao genericDao;

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    @Override
    public ReturnValue<List<CourseDTO>> search(SearchCriteria searchCriteria) {

        List<Course> courses = genericDao.find(searchCriteria, Course.class);

        List<CourseDTO> courseDTOs = new ArrayList<CourseDTO>(0);
        ReturnValue<List<CourseDTO>> returnValue = new ReturnValue<List<CourseDTO>>();
        if (courses != null && courses.size() > 0) {
            courses.forEach(course -> {
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.copyFrom(course);
                courseDTOs.add(courseDTO);
            });
            returnValue.setData(courseDTOs);
        }else{
        	returnValue.setError("Course is empty");
        }
        
        return returnValue;
    }
}
