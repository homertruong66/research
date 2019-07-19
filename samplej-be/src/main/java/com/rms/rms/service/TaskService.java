package com.rms.rms.service;

import com.rms.rms.common.dto.TaskDto;
import com.rms.rms.common.view_model.TaskCreateModel;

public interface TaskService {
    
    TaskDto create(TaskCreateModel createModel);

}