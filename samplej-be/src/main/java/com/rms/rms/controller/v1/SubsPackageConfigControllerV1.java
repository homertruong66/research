package com.rms.rms.controller.v1;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsPackageConfigUpdateModel;
import com.rms.rms.controller.SubsPackageConfigController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/subs_package_configs")
public class SubsPackageConfigControllerV1 extends SubsPackageConfigController {

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody SubsPackageConfigUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
