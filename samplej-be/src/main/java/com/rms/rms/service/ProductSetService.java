package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ProductSetDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.*;

public interface ProductSetService {

    ProductSetDto addProducts(String id, ProductSetProductsAssignModel assignModel) throws BusinessException;

    ProductSetDto create(String subsCommissionConfigId, ProductSetCreateModel createModel) throws BusinessException;

    ProductSetDto get(String id) throws BusinessException;

    ProductSetDto removeProducts(String id, ProductSetProductsUnassignModel unassignModel) throws BusinessException;

    SearchResult<ProductSetDto> search(SearchCriteria<ProductSetSearchCriteria> searchCriteria) throws BusinessException;

    ProductSetDto update(String id, ProductSetUpdateModel updateModel) throws BusinessException;
}