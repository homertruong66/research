package com.rms.rms.controller.v1;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.GuideUpdateModel;
import com.rms.rms.controller.GuideController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/guides")
public class GuideControllerV1 extends GuideController {
    
    @GetMapping(value = "/{target}")
    public ResponseDto get(@PathVariable String target) throws BusinessException {
        return super.get(target);
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody GuideUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
