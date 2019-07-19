package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.FeedbackDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.FeedbackCreateModel;
import com.rms.rms.common.view_model.FeedbackSearchCriteria;
import com.rms.rms.service.FeedbackService;
import com.rms.rms.task_processor.TaskProcessorCreateEmailAndNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.ExecutorService;

public class FeedbackController {

    @Autowired
    @Qualifier("emailExecutorService")
    private ExecutorService emailExecutorService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private TaskProcessorCreateEmailAndNotification tpCreateEmailAndNotification;

    public ResponseDto create(FeedbackCreateModel createModel) throws BusinessException {
        // create Feedback
        FeedbackDto dto = feedbackService.create(createModel);

        // send Email to Admins in background mode
        Runnable task = () -> tpCreateEmailAndNotification.processOnFeedbackCreated(dto);
        emailExecutorService.submit(task);

        return new ResponseDto(dto);
    }

    public ResponseDto search(SearchCriteria<FeedbackSearchCriteria> searchCriteria) throws BusinessException {
        SearchResult<FeedbackDto> searchResult = feedbackService.search(searchCriteria);
        return new ResponseDto(searchResult);
    }

    public ResponseDto resolve(String id) throws BusinessException {
        FeedbackDto dto = feedbackService.resolve(id);
        return new ResponseDto(dto);
    }
}
