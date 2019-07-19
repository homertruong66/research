package com.hoang.srj.service;

import com.hoang.srj.dto.UniversityDto;
import com.hoang.srj.view_model.UniversityCreateModel;
import com.hoang.srj.view_model.UniversitySearchCriteria;
import com.hoang.srj.exception.BusinessException;

import java.util.List;

public interface UniversityService {

    UniversityDto create(UniversityCreateModel createModel) throws BusinessException;

    UniversityDto get(String id) throws BusinessException;

    List<UniversityDto> search(UniversitySearchCriteria userSearchCriteria) throws BusinessException;
}
