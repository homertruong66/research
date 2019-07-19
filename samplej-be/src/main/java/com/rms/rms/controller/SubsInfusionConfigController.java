package com.rms.rms.controller;

import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.SubsInfusionAuthorizeDto;
import com.rms.rms.common.dto.SubsInfusionConfigDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubsInfusionConfigAuthorizeModel;
import com.rms.rms.service.SubsInfusionConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * homertruong
 */
public class SubsInfusionConfigController {

    private Logger logger = Logger.getLogger(SubsInfusionConfigController.class);
    
    @Autowired
    private SubsInfusionConfigService subsInfusionConfigService;

    public ResponseDto authorize(SubsInfusionConfigAuthorizeModel authorizeModel, HttpServletResponse response) throws BusinessException, IOException {
        String authorizeUrl = subsInfusionConfigService.getAuthorizeUrl(authorizeModel);
        //response.sendRedirect(authorizeUrl);  // can not do this for AJAX request
        SubsInfusionAuthorizeDto dto = new SubsInfusionAuthorizeDto();
        dto.setAuthorizeUrl(authorizeUrl);
        return new ResponseDto(dto);
    }

    public ResponseDto get(String id) throws BusinessException {
        SubsInfusionConfigDto dto = subsInfusionConfigService.get(id);
        return new ResponseDto(dto);
    }

    public void requestAccessToken(String id, String code, HttpServletResponse response) throws BusinessException, IOException {
        logger.info("requestAccessToken: " + id + " - code: " + code);
        String originReqUrl = subsInfusionConfigService.requestAccessToken(id, code);
        response.sendRedirect(originReqUrl);
    }

}
