package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.DiscountCodeCreateModel;
import com.rms.rms.common.view_model.DiscountCodeSearchCriteria;
import com.rms.rms.common.view_model.DiscountCodeUpdateModel;
import com.rms.rms.controller.DiscountCodeController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/discount_codes")
public class DiscountCodeControllerV1 extends DiscountCodeController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody DiscountCodeCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<DiscountCodeSearchCriteria> vmSearchCriteria) throws BusinessException {
        return super.search(vmSearchCriteria);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody @Valid DiscountCodeUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

}
