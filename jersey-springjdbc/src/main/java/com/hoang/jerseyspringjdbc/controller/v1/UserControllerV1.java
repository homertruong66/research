package com.hoang.jerseyspringjdbc.controller.v1;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.hoang.jerseyspringjdbc.controller.UserController;
import com.hoang.jerseyspringjdbc.exception.BusinessException;
import com.hoang.jerseyspringjdbc.view_model.UserCreateModel;
import com.hoang.jerseyspringjdbc.view_model.UserSearchCriteria;

@Component
@Path("/v1/")
public class UserControllerV1 extends UserController {

    @Path("/users")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    @Valid
    public Response create (UserCreateModel userCreateModel) throws BusinessException {
        return super.create(userCreateModel);
    }

    @Path("/users/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response get (@PathParam("id") int id) throws BusinessException {
        return super.get(id);
    }

    @Path("/users/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response search (@Valid @BeanParam UserSearchCriteria criteria) throws BusinessException {
        return super.search(criteria);
    }
}
