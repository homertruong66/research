package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PackageConfigSearchCriteria;
import com.rms.rms.common.view_model.PackageConfigUpdateModel;
import com.rms.rms.controller.PackageConfigController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/package_configs")
public class PackageConfigControllerV1 extends PackageConfigController {

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<PackageConfigSearchCriteria> vmSearchCriteria) throws BusinessException {
        return super.search(vmSearchCriteria);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody PackageConfigUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
