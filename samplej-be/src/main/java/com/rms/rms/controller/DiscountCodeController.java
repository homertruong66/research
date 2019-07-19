package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.DiscountCodeDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.DiscountCodeCreateModel;
import com.rms.rms.common.view_model.DiscountCodeSearchCriteria;
import com.rms.rms.common.view_model.DiscountCodeUpdateModel;
import com.rms.rms.service.DiscountCodeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class DiscountCodeController {

    @Autowired
    private DiscountCodeService discountCodeService;

    public ResponseDto create(DiscountCodeCreateModel createModel) throws BusinessException {
        DiscountCodeDto dto = discountCodeService.create(createModel);
        return new ResponseDto(dto);
    }

    public ResponseDto get(String id) throws BusinessException {
        DiscountCodeDto dto = discountCodeService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto search(SearchCriteria<DiscountCodeSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<DiscountCodeDto> dtoSearchResult = discountCodeService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update(String id, DiscountCodeUpdateModel updateModel) throws BusinessException {
        DiscountCodeDto dto = discountCodeService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
