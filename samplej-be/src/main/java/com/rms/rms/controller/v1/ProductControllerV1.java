package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.*;
import com.rms.rms.controller.ProductController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/products")
public class ProductControllerV1 extends ProductController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody ProductCreateModel createModel) throws BusinessException {
        return super.create(createModel);
    }

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto importProducts(@RequestBody ProductImportModel importModel) throws BusinessException {
        return super.importProducts(importModel);
    }

    @Override
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<ProductSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody ProductUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

    @Override
    @PostMapping(value = "/upload")
    public ResponseDto upload(@RequestParam("file") MultipartFile uploadedFile) throws BusinessException {
        return super.upload(uploadedFile);
    }

    @Override
    @PostMapping(value = "/{id}/view")
    public ResponseDto view(@PathVariable String id, @RequestBody ProductViewModel viewModel) throws BusinessException {
        return super.view(id, viewModel);
    }
}
