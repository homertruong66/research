package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ProductSetDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.*;
import com.rms.rms.service.ProductSetService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductSetController {

    @Autowired
    private ProductSetService productSetService;

    public ResponseDto addProducts(String id, ProductSetProductsAssignModel assignModel) throws BusinessException {
        ProductSetDto productSetDto = productSetService.addProducts(id, assignModel);
        if ( !productSetDto.getErrors().equals("") ) {
            throw new BusinessException(BusinessException.PRODUCT_SET_PRODUCT_ALREADY_ASSIGNED, productSetDto.getErrors());
        }

        return new ResponseDto(productSetDto);
    }

    public ResponseDto create (String subsCommissionConfigId, ProductSetCreateModel createModel) throws BusinessException {
        ProductSetDto productSetDto = productSetService.create(subsCommissionConfigId, createModel);
        return new ResponseDto(productSetDto);
    }

    public ResponseDto get (String id) throws BusinessException {
        ProductSetDto productSetDto = productSetService.get(id);
        return new ResponseDto(productSetDto);
    }

    public ResponseDto removeProducts(String id, ProductSetProductsUnassignModel unassignModel) throws BusinessException {
        ProductSetDto productSetDto = productSetService.removeProducts(id, unassignModel);
        if ( !productSetDto.getErrors().equals("") ) {
            throw new BusinessException(BusinessException.PRODUCT_SET_PRODUCT_NOT_IN,
                                        productSetDto.getErrors());
        }
        return new ResponseDto(productSetDto);
    }

    public ResponseDto search (SearchCriteria<ProductSetSearchCriteria> searchCriteria) throws BusinessException {
        SearchResult<ProductSetDto> dtoSearchResult = productSetService.search(searchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update (String id, ProductSetUpdateModel updateModel) throws BusinessException {
        ProductSetDto productSetDto = productSetService.update(id, updateModel);
        return new ResponseDto(productSetDto);
    }
}
