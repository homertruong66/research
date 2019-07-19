package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.NotificationDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.NotificationSearchCriteria;
import com.rms.rms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    public void delete(String id) throws BusinessException {
        notificationService.delete(id);
    }

    public ResponseDto markAsRead(String id) throws BusinessException {
        NotificationDto notificationDto = notificationService.markAsRead(id);
        return new ResponseDto(notificationDto);
    }

    public ResponseDto search (SearchCriteria<NotificationSearchCriteria> searchCriteria) throws BusinessException {
        SearchResult<NotificationDto> dtoSearchCriteria = notificationService.search(searchCriteria);
        return new ResponseDto(dtoSearchCriteria);
    }
}
