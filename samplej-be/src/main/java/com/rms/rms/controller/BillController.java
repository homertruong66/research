package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.BillDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.BillSearchCriteria;
import com.rms.rms.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;

public class BillController {

    @Autowired
    private BillService billService;

    public ResponseDto confirm(String id) throws BusinessException {
        BillDto dto = billService.confirm(id);
        return new ResponseDto(dto);
    }

    public void generate() throws BusinessException {
        billService.generate();
    }

    public ResponseDto search(SearchCriteria<BillSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<BillDto> dtoSearchResult = billService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }
}