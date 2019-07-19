package com.rms.rms.controller.v1;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PersonUpdateModel;
import com.rms.rms.controller.ProfileController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/profiles")
public class ProfileControllerV1 extends ProfileController {

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseDto update(@PathVariable String id, @RequestBody PersonUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }
}