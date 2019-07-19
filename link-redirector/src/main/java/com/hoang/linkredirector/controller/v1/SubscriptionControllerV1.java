package com.hoang.linkredirector.controller.v1;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.hoang.linkredirector.exception.BusinessException;
import com.hoang.linkredirector.controller.SubscriptionController;
import com.hoang.linkredirector.view_model.SubscriptionCreateParam;

@Component
@Path("/v1/")
public class SubscriptionControllerV1 extends SubscriptionController {

    @Path("/users/{user_id}/subscriptions")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create (@PathParam("user_id") int userId,
                            SubscriptionCreateParam subscriptionCreateParam) throws IOException, BusinessException {
        return super.create(userId, subscriptionCreateParam);
    }


    @Path("/subscriptions/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get (@PathParam("id") int id) throws Exception {
        return super.get(id);
    }
}
