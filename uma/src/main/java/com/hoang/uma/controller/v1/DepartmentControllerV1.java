package com.hoang.uma.controller.v1;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.DepartmentCreateModel;
import com.hoang.uma.common.view_model.DepartmentSearchCriteria;
import com.hoang.uma.common.view_model.DepartmentUpdateModel;
import com.hoang.uma.controller.DepartmentController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * homertruong
 */

@RestController
public class DepartmentControllerV1 extends DepartmentController {

    @RequestMapping(value = "/v1/universities/{id}/departments", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create (@PathVariable(name = "id") String universityId,
                               @RequestBody @Valid DepartmentCreateModel createModel) throws BusinessException
    {
        return super.create(universityId, createModel);
    }

    @RequestMapping(value = "/v1/departments/{id}", method = { RequestMethod.DELETE })
    public void delete (@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @RequestMapping(value = "/v1/departments/{id}", method = { RequestMethod.GET })
    public ResponseDto get (@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @RequestMapping(value = "/v1/departments/search", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search (@RequestBody @Valid DepartmentSearchCriteria searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @RequestMapping(value = "/v1/departments/{id}", method = { RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update (@PathVariable String id,
                          @RequestBody @Valid DepartmentUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
