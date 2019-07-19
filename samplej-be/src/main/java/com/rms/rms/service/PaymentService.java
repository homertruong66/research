package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.PaymentDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PaymentCreateModel;
import com.rms.rms.common.view_model.PaymentSearchCriteria;
import com.rms.rms.common.view_model.PaymentUpdateModel;

import java.util.Date;

/**
 * homertruong
 */

public interface PaymentService {

    PaymentDto approve (String id) throws BusinessException;

    PaymentDto create (PaymentCreateModel createModel) throws BusinessException;

    byte[] export(Date from, Date to) throws BusinessException;

    PaymentDto get (String id) throws BusinessException;

    PaymentDto reject (String id, String reason) throws BusinessException;

    SearchResult<PaymentDto> search (SearchCriteria<PaymentSearchCriteria> vmSearchCriteria) throws BusinessException;

    PaymentDto update (String id, PaymentUpdateModel updateModel) throws BusinessException;

}
