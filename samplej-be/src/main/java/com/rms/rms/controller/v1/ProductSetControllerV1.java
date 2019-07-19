package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.*;
import com.rms.rms.controller.ProductSetController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class ProductSetControllerV1 extends ProductSetController {

    @Override
    @PostMapping(value = "/product_sets/{id}/product_sets_products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto addProducts(@PathVariable String id,
                                     @RequestBody ProductSetProductsAssignModel assignModel) throws BusinessException {
        return super.addProducts(id, assignModel);
    }

    @Override
    @PostMapping(value = "/subs_commission_configs/{subsCommissionConfigId}/product_sets", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create (@PathVariable String subsCommissionConfigId,
                               @RequestBody ProductSetCreateModel createModel) throws BusinessException {
        return super.create(subsCommissionConfigId, createModel);
    }

    @Override
    @GetMapping(value = "/product_sets/{id}")
    public ResponseDto get (@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/product_sets/{id}/product_sets_products",
                   consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto removeProducts(@PathVariable String id, @RequestBody ProductSetProductsUnassignModel unassignModel) throws BusinessException {
        return super.removeProducts(id, unassignModel);
    }

    @Override
    @PostMapping(value = "/product_sets/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search (@RequestBody SearchCriteria<ProductSetSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @Override
    @PutMapping(value = "/product_sets/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update (@PathVariable String id, @RequestBody ProductSetUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }
}