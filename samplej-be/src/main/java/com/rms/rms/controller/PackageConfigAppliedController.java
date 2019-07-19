package com.rms.rms.controller;

import com.rms.rms.common.dto.PackageConfigAppliedDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.service.PackageConfigAppliedService;
import org.springframework.beans.factory.annotation.Autowired;

public class PackageConfigAppliedController {
    
    @Autowired
    private PackageConfigAppliedService packageConfigAppliedService;

    public ResponseDto get(String id) throws BusinessException {
        PackageConfigAppliedDto dto = packageConfigAppliedService.get(id);
        return new ResponseDto(dto);
    }

}
