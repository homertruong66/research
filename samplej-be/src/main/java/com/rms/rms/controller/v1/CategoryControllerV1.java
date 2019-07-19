package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.CategoryCreateModel;
import com.rms.rms.common.view_model.CategorySearchCriteria;
import com.rms.rms.common.view_model.CategoryUpdateModel;
import com.rms.rms.controller.CategoryController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/categories")
public class CategoryControllerV1 extends CategoryController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody CategoryCreateModel createModel) throws BusinessException {
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
    public ResponseDto search(@RequestBody SearchCriteria<CategorySearchCriteria> vmSearchCriteria) throws BusinessException {
        return super.search(vmSearchCriteria);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody @Valid CategoryUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }
}
