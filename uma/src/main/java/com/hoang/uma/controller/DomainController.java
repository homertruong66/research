package com.hoang.uma.controller;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */

public class DomainController {

    @Autowired
    protected DomainService domainService;

    public ResponseDto getCoutries () throws BusinessException {
        return new ResponseDto(domainService.getCountries());
    }

}
