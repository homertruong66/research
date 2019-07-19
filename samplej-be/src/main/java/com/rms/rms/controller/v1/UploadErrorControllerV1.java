package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.UploadErrorSearchCriteria;
import com.rms.rms.controller.UploadErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UploadErrorControllerV1 extends UploadErrorController {

    @PostMapping(value = "/v1/upload_errors/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search (@RequestBody SearchCriteria<UploadErrorSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

}
