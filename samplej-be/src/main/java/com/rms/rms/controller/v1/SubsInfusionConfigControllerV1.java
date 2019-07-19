package com.rms.rms.controller.v1;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsInfusionConfigAuthorizeModel;
import com.rms.rms.controller.SubsInfusionConfigController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/subs_infusion_configs")
public class SubsInfusionConfigControllerV1 extends SubsInfusionConfigController {

    @PostMapping(value = "/authorize", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto authorize(@RequestBody SubsInfusionConfigAuthorizeModel authorizeModel, HttpServletResponse response) throws BusinessException, IOException {
        return super.authorize(authorizeModel, response);
    }

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @GetMapping(value = "/code/{id}")
    public void requestAccessToken(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws BusinessException, IOException {
        String code = request.getParameter("code");
        super.requestAccessToken(id, code, response);
    }

}
