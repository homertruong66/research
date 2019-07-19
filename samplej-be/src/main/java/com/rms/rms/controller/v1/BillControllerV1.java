package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.BillSearchCriteria;
import com.rms.rms.controller.BillController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/bills")
public class BillControllerV1 extends BillController {

    @PostMapping(value = "/{id}/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto confirm(@PathVariable String id) throws BusinessException {
        return super.confirm(id);
    }

    @GetMapping(value = "/generate")
    public void generate() throws BusinessException {
        super.generate();
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<BillSearchCriteria> vmSearchCriteria) throws BusinessException {
        return super.search(vmSearchCriteria);
    }
}