package com.rms.rms.controller.v1;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsAlertConfigUpdateModel;
import com.rms.rms.controller.SubsAlertConfigController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/subs_alert_configs")
public class SubsAlertConfigControllerV1 extends SubsAlertConfigController {

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @GetMapping(value = "/list")
    public ResponseDto list() throws BusinessException {
        return super.list();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody SubsAlertConfigUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
