package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.DomainSearchCriteria;
import com.rms.rms.controller.DomainController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * homertruong
 */

@RestController
public class DomainControllerV1 extends DomainController {

    @PostMapping(value = "/v1/domains/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<DomainSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }
}
