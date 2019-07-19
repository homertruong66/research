package com.hoang.srj.controller;

import com.hoang.srj.dto.UniversityDto;
import com.hoang.srj.exception.BusinessException;
import com.hoang.srj.service.UniversityService;
import com.hoang.srj.view_model.UniversityCreateModel;
import com.hoang.srj.view_model.UniversitySearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UniversityController {

    @Autowired
    protected UniversityService universityService;

    public UniversityDto create (UniversityCreateModel createModel) throws BusinessException {
        return universityService.create(createModel);
    }

    public UniversityDto get (String id) throws BusinessException {
        return universityService.get(id);
    }

    public List<UniversityDto> search (UniversitySearchCriteria searchCriteria) throws BusinessException {
        return universityService.search(searchCriteria);
    }

}
