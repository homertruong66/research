package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.VoucherDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.VoucherCreateModel;
import com.rms.rms.common.view_model.VoucherSearchCriteria;
import com.rms.rms.common.view_model.VoucherSendModel;
import com.rms.rms.common.view_model.VoucherUpdateModel;
import com.rms.rms.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    public ResponseDto create(VoucherCreateModel createModel) throws BusinessException {
        VoucherDto dto = voucherService.create(createModel);
        return new ResponseDto(dto);
    }

    public void delete(String id) throws BusinessException {
        voucherService.delete(id);
    }

    public ResponseDto get(String id) throws BusinessException {
        VoucherDto dto = voucherService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto search(SearchCriteria<VoucherSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<VoucherDto> dtoSearchResult = voucherService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public void send(VoucherSendModel sendModel) throws BusinessException, IOException {
        voucherService.send(sendModel);
    }

    public ResponseDto update(String id, VoucherUpdateModel updateModel) throws BusinessException {
        VoucherDto dto = voucherService.update(id, updateModel);
        return new ResponseDto(dto);
    }
}
