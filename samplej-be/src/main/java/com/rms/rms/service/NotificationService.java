package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.NotificationDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.NotificationCreateModel;
import com.rms.rms.common.view_model.NotificationSearchCriteria;

import java.util.List;

public interface NotificationService {

    NotificationDto create (NotificationCreateModel createModel) throws BusinessException;

    List<NotificationDto> create (List<NotificationCreateModel> createModels) throws BusinessException;

    void delete (String id) throws BusinessException;

    NotificationDto markAsRead (String id) throws BusinessException;

    SearchResult<NotificationDto> search (SearchCriteria<NotificationSearchCriteria> vmSearchCriteria) throws BusinessException;
}
