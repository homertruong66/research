package com.hoang.srj.service;

import com.hoang.srj.dao.GenericDao;
import com.hoang.srj.dto.HealthDto;
import com.hoang.srj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {

    @Autowired
    private GenericDao genericDao;

    @Override
    public HealthDto checkHealth() {
        HealthDto healthDto = new HealthDto();
        healthDto.setAppStatus("App Server OK!");

        try {
            genericDao.countAll(User.class);
            healthDto.setDbMasterStatus("DB Master OK!");
            healthDto.setDbSlaveStatus("DB Slave OK!");
        }
        catch (Exception ex) {
            healthDto.setDbMasterStatus("DB Master has problem!");
            healthDto.setDbSlaveStatus("DB Slave has problem!");
        }

        return healthDto;
    }
}
