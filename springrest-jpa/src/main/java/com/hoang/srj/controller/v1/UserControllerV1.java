package com.hoang.srj.controller.v1;

import com.hoang.srj.view_model.UserUpdateModel;
import com.hoang.srj.controller.UserController;
import com.hoang.srj.dto.UserDto;
import com.hoang.srj.exception.BusinessException;
import com.hoang.srj.view_model.UserCreateModel;
import com.hoang.srj.view_model.UserSearchCriteria;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserControllerV1 extends UserController {

    @RequestMapping(value = "/v1/users", method = { RequestMethod.POST })
    public UserDto create (@Valid UserCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @RequestMapping(value = "/v1/users/{id}", method = { RequestMethod.DELETE })
    public void delete (@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @RequestMapping(value = "/v1/users/{id}", method = { RequestMethod.GET })
    public UserDto get (@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @RequestMapping(value = "/v1/users/search", method = { RequestMethod.GET })
    public List<UserDto> search (@Valid UserSearchCriteria searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @RequestMapping(value = "/v1/users/{id}", method = { RequestMethod.PUT })
    public UserDto update (@Valid UserUpdateModel userUpdateModel) throws BusinessException {
        return super.update(userUpdateModel);
    }

}
