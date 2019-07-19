package com.hoang.uma.controller.v1;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.common.view_model.TeacherCreateModel;
import com.hoang.uma.common.view_model.TeacherSearchCriteria;
import com.hoang.uma.common.view_model.TeacherUpdateModel;
import com.hoang.uma.controller.TeacherController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * homertruong
 */

@RestController
public class TeacherControllerV1 extends TeacherController {

    @RequestMapping(value = "/v1/teachers/{id}/assign", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto assign (@PathVariable String id, @RequestBody List<String> departmentIds) throws BusinessException {
        return super.assign(id, departmentIds);
    }

    @RequestMapping(value = "/v1/teachers", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create (@RequestBody @Valid TeacherCreateModel createModel) throws BusinessException
    {
        return super.create(createModel);
    }

    @RequestMapping(value = "/v1/teachers/{id}", method = { RequestMethod.DELETE })
    public void delete (@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @RequestMapping(value = "/v1/teachers/{id}", method = { RequestMethod.GET })
    public ResponseDto get (@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @RequestMapping(value = "/v1/teachers/search", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search (@RequestBody @Valid TeacherSearchCriteria searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @RequestMapping(value = "/v1/teachers/{id}", method = { RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update (@PathVariable String id, @RequestBody @Valid TeacherUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

    @RequestMapping(value = "/v1/teachers/upload", method = { RequestMethod.POST })
    public String upload (@RequestParam("file") MultipartFile uploadedFile) {
        return super.upload (uploadedFile);
    }

}
