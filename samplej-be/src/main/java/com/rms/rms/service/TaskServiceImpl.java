package com.rms.rms.service;

import com.rms.rms.common.dto.TaskDto;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.TaskCreateModel;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.service.model.Task;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class TaskServiceImpl implements TaskService {

    private Logger logger = Logger.getLogger(TaskServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private GenericDao genericDao;
    
    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TaskDto create(TaskCreateModel createModel) {
        logger.info("create: " + createModel);

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }
        Task newDO = beanMapper.map(createModel, Task.class);

        // validate biz rules

        // do authorization

        // do biz action
        Task pdo = genericDao.create(newDO);

        return beanMapper.map(pdo, TaskDto.class);
    }
}