package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.AdminCreateModel;
import com.rms.rms.common.view_model.AdminSearchCriteria;
import com.rms.rms.common.view_model.AdminUpdateModel;
import com.rms.rms.controller.AdminController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/admins")
public class AdminControllerV1 extends AdminController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody AdminCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<AdminSearchCriteria> vmSearchCriteria) throws BusinessException {
        return super.search(vmSearchCriteria);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody @Valid AdminUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
