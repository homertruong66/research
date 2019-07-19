package com.hoang.srj.service;

import com.hoang.srj.dto.UniversityDto;
import com.hoang.srj.view_model.UniversitySearchCriteria;
import com.hoang.srj.dao.GenericDao;
import com.hoang.srj.dao.UniversityDao;
import com.hoang.srj.exception.BusinessException;
import com.hoang.srj.model.University;
import com.hoang.srj.view_model.UniversityCreateModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UniversityServiceImpl implements UniversityService {

    private Logger logger = Logger.getLogger(UniversityServiceImpl.class);

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private UniversityDao universityDao;

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UniversityDto create (UniversityCreateModel createModel) throws BusinessException {
        logger.info("create a University: " + createModel.toString());

        // set data for University
        University university = new University();
        university.setId(UUID.randomUUID().toString());
        university.setName(createModel.getName());
        university.setCreatedAt(new Date());
        university.setUpdatedAt(new Date());

        // create University
        genericDao.create(university);

        // create UniversityDto to return
        UniversityDto universityDto = new UniversityDto();
        universityDto.copyFrom(university);

        return universityDto;
    }

    @Override
    public UniversityDto get (String id) throws BusinessException {
        logger.info("get a University: " + id);

        // get University
        University university = genericDao.read(University.class, id);
        if ( university == null ) {
            throw new BusinessException(BusinessException.UNIVERSITY_NOT_FOUND,
                                        BusinessException.UNIVERSITY_NOT_FOUND_MESSAGE);
        }

        // create UniversityDto to return
        UniversityDto universityDto = new UniversityDto();
        universityDto.copyFrom(university);

        return universityDto;
    }

    @Override
    public List<UniversityDto> search (UniversitySearchCriteria searchCriteria) throws BusinessException {
        List<UniversityDto> result = new ArrayList<>();

        List<University> universities = universityDao.search(searchCriteria);
        universities.forEach(university -> {
            UniversityDto universityDto = new UniversityDto();
            universityDto.copyFrom(university);
            result.add(universityDto);
        });

        return result;
    }
}
