package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.ChannelCreateModel;
import com.rms.rms.common.view_model.ChannelSearchCriteria;
import com.rms.rms.common.view_model.ChannelUpdateModel;
import com.rms.rms.controller.ChannelController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/channels")
public class ChannelControllerV1 extends ChannelController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody ChannelCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<ChannelSearchCriteria> vmSearchCriteria) throws BusinessException {
        return super.search(vmSearchCriteria);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody @Valid ChannelUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

    @Override
    @PostMapping(value = "/{id}/view")
    public ResponseDto view(@PathVariable String id) throws BusinessException {
        return super.view(id);
    }

}
