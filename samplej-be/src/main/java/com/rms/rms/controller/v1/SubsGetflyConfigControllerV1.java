package com.rms.rms.controller.v1;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsGetflyConfigUpdateModel;
import com.rms.rms.controller.SubsGetflyConfigController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/subs_getfly_configs")
public class SubsGetflyConfigControllerV1 extends SubsGetflyConfigController {

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @GetMapping(value = "/{id}/test")
    public ResponseDto test(@PathVariable String id) throws BusinessException {
        return super.test(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody SubsGetflyConfigUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
