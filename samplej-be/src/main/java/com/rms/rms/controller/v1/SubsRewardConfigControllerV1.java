package com.rms.rms.controller.v1;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsRewardConfigUpdateModel;
import com.rms.rms.controller.SubsRewardConfigController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/subs_reward_configs")
public class SubsRewardConfigControllerV1 extends SubsRewardConfigController {

    @Override
    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody SubsRewardConfigUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }
}
