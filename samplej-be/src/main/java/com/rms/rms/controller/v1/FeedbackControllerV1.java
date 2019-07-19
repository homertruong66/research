package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.FeedbackCreateModel;
import com.rms.rms.common.view_model.FeedbackSearchCriteria;
import com.rms.rms.controller.FeedbackController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/feedbacks")
public class FeedbackControllerV1 extends FeedbackController {

    @Override
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody FeedbackCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @Override
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<FeedbackSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @Override
    @PostMapping(value = "/{id}/resolve", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto resolve(@PathVariable String id) throws BusinessException {
        return super.resolve(id);
    }
}
