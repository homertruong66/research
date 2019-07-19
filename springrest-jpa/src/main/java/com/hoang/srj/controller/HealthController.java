package com.hoang.srj.controller;

import com.hoang.srj.dto.HealthDto;
import com.hoang.srj.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;

public class HealthController {

    @Autowired
    protected HealthCheckService healthCheckService;

    public HealthDto checkHealth () {
        return healthCheckService.checkHealth();
    }

}
