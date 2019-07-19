package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.VoucherCreateModel;
import com.rms.rms.common.view_model.VoucherSearchCriteria;
import com.rms.rms.common.view_model.VoucherSendModel;
import com.rms.rms.common.view_model.VoucherUpdateModel;
import com.rms.rms.controller.VoucherController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/v1/vouchers")
public class VoucherControllerV1 extends VoucherController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody VoucherCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) throws BusinessException {
        super.delete(id);
    }

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<VoucherSearchCriteria> vmSearchCriteria) throws BusinessException {
        return super.search(vmSearchCriteria);
    }

    @PostMapping(value = "/process", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void send(@RequestBody VoucherSendModel sendModel) throws BusinessException, IOException {
        super.send(sendModel);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody VoucherUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }
}
