package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.CategoryDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.CategoryCreateModel;
import com.rms.rms.common.view_model.CategorySearchCriteria;
import com.rms.rms.common.view_model.CategoryUpdateModel;

public interface CategoryService {
    
    CategoryDto create(CategoryCreateModel createModel) throws BusinessException;

    void delete(String id) throws BusinessException;

    CategoryDto get(String id) throws BusinessException;

    SearchResult<CategoryDto> search(SearchCriteria<CategorySearchCriteria> vmSearchCriteria) throws BusinessException;

    CategoryDto update(String id, CategoryUpdateModel updateModel) throws BusinessException;
    
}