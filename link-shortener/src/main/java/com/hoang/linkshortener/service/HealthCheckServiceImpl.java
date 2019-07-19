package com.hoang.linkshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoang.linkshortener.dao.UserDao;
import com.hoang.linkshortener.dto.HealthDto;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {

    public static final String JERSEY_STATUS_OK          = "App Server OK!";
    public static final String MODEL_DB_MASTER_STATUS_OK = "Model DB Master OK!";
    public static final String MODEL_DB_SLAVE_STATUS_OK  = "Model DB Slave OK!";

    @Autowired
    private UserDao userDao;

    @Override
    public HealthDto checkHealth () {
        HealthDto healthDto = new HealthDto();

        healthDto.setJerseyStatus(JERSEY_STATUS_OK);

        if ( userDao.checkMasterModelsDbConnection() ) {
            healthDto.setModelDbMasterStatus(MODEL_DB_MASTER_STATUS_OK);
        }

        if ( userDao.checkSlaveModelsDbConnection() ) {
            healthDto.setModelDbSlaveStatus(MODEL_DB_SLAVE_STATUS_OK);
        }

        return healthDto;
    }
}
