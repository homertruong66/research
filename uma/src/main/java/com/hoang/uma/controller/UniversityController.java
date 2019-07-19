package com.hoang.uma.controller;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.UniversityCreateModel;
import com.hoang.uma.common.view_model.UniversitySearchCriteria;
import com.hoang.uma.common.view_model.UniversityUpdateModel;
import com.hoang.uma.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

/**
 * homertruong
 */

public class UniversityController {

    @Autowired
    protected UniversityService universityService;

    public ResponseDto create (@Valid UniversityCreateModel createModel) throws BusinessException {
        return new ResponseDto(universityService.create(createModel));
    }

    public void delete (String id) throws BusinessException {
        universityService.delete(id);
    }

    public ResponseDto get (String id) throws BusinessException {
        return new ResponseDto(universityService.get(id));
    }

    public ResponseDto search (UniversitySearchCriteria searchCriteria) throws BusinessException {
        return new ResponseDto(universityService.search(searchCriteria));
    }

    public ResponseDto update (String id, @Valid UniversityUpdateModel updateModel) throws BusinessException {
        return new ResponseDto(universityService.update(id, updateModel));
    }
}
