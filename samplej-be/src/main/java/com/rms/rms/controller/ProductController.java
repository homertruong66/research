package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ProductDto;
import com.rms.rms.common.dto.ProductViewDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.*;
import com.rms.rms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class ProductController {

    @Autowired
    private ProductService productService;

    public ResponseDto create(ProductCreateModel createModel) throws BusinessException {
        ProductDto dto = productService.create(createModel);
        return new ResponseDto(dto);
    }

    public ResponseDto get(String id) throws BusinessException {
        ProductDto dto = productService.get(id);
        return new ResponseDto(dto);
    }

    public ResponseDto importProducts(ProductImportModel importModel) throws BusinessException {
        Set<ProductDto> dtos = productService.importProducts(importModel);
        return new ResponseDto(dtos);
    }

    public ResponseDto search(SearchCriteria<ProductSearchCriteria> searchCriteria) throws BusinessException {
        SearchResult<ProductDto> dtoSearchResult = productService.search(searchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update(String id, ProductUpdateModel updateModel) throws BusinessException {
        ProductDto dto = productService.update(id, updateModel);
        return new ResponseDto(dto);
    }

    public ResponseDto upload(MultipartFile uploadedFile) throws BusinessException {
        productService.upload(uploadedFile);

        return new ResponseDto("ok");
    }

    public ResponseDto view(String id, ProductViewModel viewModel) throws BusinessException {
        ProductViewDto productViewDto = productService.view(id, viewModel);
        return new ResponseDto(productViewDto);
    }
}
