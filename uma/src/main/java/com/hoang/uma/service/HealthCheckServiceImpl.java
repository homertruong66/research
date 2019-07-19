package com.hoang.uma.service;

import com.hoang.uma.common.dto.HealthDto;
import com.hoang.uma.persistence.GenericDao;
import com.hoang.uma.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * homertruong
 */

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
