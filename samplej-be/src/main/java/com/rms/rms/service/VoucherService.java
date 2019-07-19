package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.VoucherDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.VoucherCreateModel;
import com.rms.rms.common.view_model.VoucherSearchCriteria;
import com.rms.rms.common.view_model.VoucherSendModel;
import com.rms.rms.common.view_model.VoucherUpdateModel;

import java.io.IOException;

public interface VoucherService {
    
    VoucherDto create(VoucherCreateModel createModel) throws BusinessException;

    void delete(String id) throws BusinessException;

    VoucherDto get(String id) throws BusinessException;

    SearchResult<VoucherDto> search(SearchCriteria<VoucherSearchCriteria> vmSearchCriteria) throws BusinessException;

    void send(VoucherSendModel sendModel) throws BusinessException, IOException;

    VoucherDto update(String id, VoucherUpdateModel updateModel) throws BusinessException;
    
}