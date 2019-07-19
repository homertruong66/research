package com.hoang.uma.controller.v1;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.CourseCreateModel;
import com.hoang.uma.common.view_model.CourseSearchCriteria;
import com.hoang.uma.common.view_model.CourseUpdateModel;
import com.hoang.uma.controller.CourseController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * homertruong
 */

@RestController
public class CourseControllerV1 extends CourseController {

    @RequestMapping(value = "/v1/departments/{id}/courses", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create (@PathVariable(name = "id") String departmentId,
                               @RequestBody @Valid CourseCreateModel createModel) throws BusinessException
    {
        return super.create(departmentId, createModel);
    }

    @RequestMapping(value = "/v1/courses/{id}", method = { RequestMethod.DELETE })
    public void delete (@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @RequestMapping(value = "/v1/courses/{id}", method = { RequestMethod.GET })
    public ResponseDto get (@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @RequestMapping(value = "/v1/courses/search", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search (@RequestBody @Valid CourseSearchCriteria searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @RequestMapping(value = "/v1/courses/{id}", method = { RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update (@PathVariable String id,
                          @RequestBody @Valid CourseUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
