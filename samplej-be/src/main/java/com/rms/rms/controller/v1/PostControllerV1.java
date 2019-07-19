package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PostCreateModel;
import com.rms.rms.common.view_model.PostSearchCriteria;
import com.rms.rms.common.view_model.PostUpdateModel;
import com.rms.rms.controller.PostController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/posts")
public class PostControllerV1 extends PostController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody PostCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<PostSearchCriteria> vmSearchCriteria) throws BusinessException {
        return super.search(vmSearchCriteria);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update (@PathVariable String id, @RequestBody PostUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }
}
