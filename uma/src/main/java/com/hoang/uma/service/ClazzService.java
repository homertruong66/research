package com.hoang.uma.service;

import com.hoang.uma.common.view_model.ClazzCreateModel;
import com.hoang.uma.common.dto.ClazzDto;
import com.hoang.uma.common.dto.SearchResultDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.ClazzSearchCriteria;
import com.hoang.uma.common.view_model.ClazzUpdateModel;

/**
 * homertruong
 */

public interface ClazzService {

    String cancel (String id) throws BusinessException;

    String create (String courseId, ClazzCreateModel createModel) throws BusinessException;

    void delete (String id) throws BusinessException;

    String end (String id) throws BusinessException;

    ClazzDto get (String id) throws BusinessException;

    String register (String id, String email) throws BusinessException;

    SearchResultDto<ClazzDto> search (ClazzSearchCriteria searchCriteria) throws BusinessException;

    String start (String id) throws BusinessException;

    String unregister (String id, String email) throws BusinessException;

    String update (String id, ClazzUpdateModel updateModel) throws BusinessException;

}
