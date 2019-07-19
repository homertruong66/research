package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.SubscriberCreateModel;
import com.rms.rms.common.view_model.SubscriberExtendModel;
import com.rms.rms.common.view_model.SubscriberSearchCriteria;
import com.rms.rms.common.view_model.SubscriberUpdateModel;
import com.rms.rms.controller.SubscriberController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/subscribers")
public class SubscriberControllerV1 extends SubscriberController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody SubscriberCreateModel createModel) throws BusinessException, IOException {
        return super.create(createModel);
    }

    @PostMapping(value = "/{id}/extend", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto extend(@PathVariable String id,@RequestBody SubscriberExtendModel extendModel) throws BusinessException {
        return super.extend(id, extendModel);
    }

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @PostMapping(value = "/{id}/renew", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto renew(@PathVariable String id) throws BusinessException, IOException{
        return super.renew(id);
    }

    @PostMapping(value = "/{id}/reset_data", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto resetData(@PathVariable String id) throws BusinessException {
        return super.resetData(id);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<SubscriberSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody @Valid SubscriberUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

    @PostMapping(value = "/{id}/upgrade/{packageType}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto upgrade(@PathVariable String id, @PathVariable String packageType) throws BusinessException, IOException {
        return super.upgrade(id, packageType);
    }

    @Override
    @PostMapping(value = "/upload")
    public ResponseDto upload(@RequestParam("file") MultipartFile uploadedFile) throws BusinessException {
        return super.upload(uploadedFile);
    }

    @Override
    @PostMapping(value = "/{id}/view")
    public ResponseDto view(@PathVariable String id) throws BusinessException {
        return super.view(id);
    }

}
