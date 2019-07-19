package com.rms.rms.controller.v1;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsEarnerConfigUpdateModel;
import com.rms.rms.controller.SubsEarnerConfigController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/subs_earner_configs")
public class SubsEarnerConfigControllerV1 extends SubsEarnerConfigController {

    @Override
    @GetMapping(value = "/{id}")
    public ResponseDto get (@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update (@PathVariable String id,
                               @RequestBody SubsEarnerConfigUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }
}
