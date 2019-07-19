package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.ShareSearchCriteria;
import com.rms.rms.common.view_model.ShareStatsUpdateModel;
import com.rms.rms.controller.ShareController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/shares")
public class ShareControllerV1 extends ShareController {

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<ShareSearchCriteria> vmSearchCriteria) throws BusinessException {
        return super.search(vmSearchCriteria);
    }

    @PostMapping(value = "/stats", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto updateStats(@RequestBody ShareStatsUpdateModel statsUpdateModel) throws BusinessException {
        return super.updateStats(statsUpdateModel);
    }

}
