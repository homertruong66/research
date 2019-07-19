package com.hoang.linkshortener.controller.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.hoang.linkshortener.controller.HealthController;

@Component
@Path("/v1/")
public class HealthControllerV1 extends HealthController {

    @Path("/healthcheck")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkHealth () {
        return super.checkHealth();
    }
}
