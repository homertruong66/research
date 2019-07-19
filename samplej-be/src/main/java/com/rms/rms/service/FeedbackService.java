package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.FeedbackDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.FeedbackCreateModel;
import com.rms.rms.common.view_model.FeedbackSearchCriteria;

public interface FeedbackService {

    FeedbackDto create(FeedbackCreateModel createModel) throws BusinessException;

    FeedbackDto resolve(String id) throws BusinessException;

    SearchResult<FeedbackDto> search(SearchCriteria<FeedbackSearchCriteria> vmSearchCriteria) throws BusinessException;
}
