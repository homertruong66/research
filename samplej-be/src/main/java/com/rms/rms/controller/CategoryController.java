package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.CategoryDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.CategoryCreateModel;
import com.rms.rms.common.view_model.CategorySearchCriteria;
import com.rms.rms.common.view_model.CategoryUpdateModel;
import com.rms.rms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public ResponseDto create(CategoryCreateModel createModel) throws BusinessException {
        CategoryDto dto = categoryService.create(createModel);
        return new ResponseDto(dto);
    }

    public void delete(String id) throws BusinessException {
        categoryService.delete(id);
    }

    public ResponseDto get(String id) throws BusinessException {
        CategoryDto dto = categoryService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto search(SearchCriteria<CategorySearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<CategoryDto> dtoSearchResult = categoryService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update(String id, CategoryUpdateModel updateModel) throws BusinessException {
        CategoryDto dto = categoryService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
