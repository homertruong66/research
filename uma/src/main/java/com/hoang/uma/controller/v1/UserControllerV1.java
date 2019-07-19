package com.hoang.uma.controller.v1;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.UserCreateModel;
import com.hoang.uma.common.view_model.UserSearchCriteria;
import com.hoang.uma.common.view_model.UserUpdateModel;
import com.hoang.uma.controller.UserController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * homertruong
 */

@RestController
public class UserControllerV1 extends UserController {

    @RequestMapping(value = "/v1/users", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create (@RequestBody @Valid UserCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @RequestMapping(value = "/v1/users/{id}", method = { RequestMethod.DELETE })
    public void delete (@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @RequestMapping(value = "/v1/users/{id}", method = { RequestMethod.GET })
    public ResponseDto get (@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @RequestMapping(value = "/v1/users/", method = { RequestMethod.GET })
    public ResponseDto get () throws BusinessException {
        return super.get(2);
    }

    @RequestMapping(value = "/v1/users/search", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search (@RequestBody @Valid UserSearchCriteria searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @RequestMapping(value = "/v1/users/{id}", method = { RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update (@PathVariable String id, @RequestBody @Valid UserUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
