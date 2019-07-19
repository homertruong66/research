package com.hoang.uma.controller;

import com.hoang.uma.common.dto.HealthDto;
import com.hoang.uma.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */

public class HealthController {

    @Autowired
    protected HealthCheckService healthCheckService;

    public HealthDto checkHealth () {
        return healthCheckService.checkHealth();
    }

}
