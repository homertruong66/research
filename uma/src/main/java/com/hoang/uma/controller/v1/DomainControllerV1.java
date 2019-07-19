package com.hoang.uma.controller.v1;

import com.hoang.uma.common.dto.ResponseDto;
import com.hoang.uma.common.exception.BusinessException;
import com.hoang.uma.controller.DomainController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * homertruong
 */

@RestController
public class DomainControllerV1 extends DomainController {

    @RequestMapping(value = "/v1/countries", method = { RequestMethod.GET })
    public ResponseDto getCountries () throws BusinessException {
        return super.getCoutries();
    }

}
