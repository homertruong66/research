package com.hoang.linkredirector.controller;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hoang.linkredirector.service.HealthCheckService;

public class HealthController {

    @Autowired
    protected HealthCheckService healthCheckService;

    public Response checkHealth () {
        return Response.ok(healthCheckService.checkHealth()).build();
    }

}
