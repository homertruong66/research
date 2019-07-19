package com.rms.rms.controller.v1;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsConfigUpdateModel;
import com.rms.rms.controller.SubsConfigController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/subs_configs")
public class SubsConfigControllerV1 extends SubsConfigController {

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @GetMapping(value = "/{id}/terms_and_conditions")
    public ResponseDto getTermsAndConditions(@PathVariable String id) throws BusinessException {
        return super.getTermsAndConditions(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody SubsConfigUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
