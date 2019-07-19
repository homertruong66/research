package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ProductDto;
import com.rms.rms.common.dto.ProductViewDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface ProductService {

    ProductDto create(ProductCreateModel createModel) throws BusinessException;

    ProductDto get(String id) throws BusinessException;

    Set<ProductDto> importProducts(ProductImportModel importModel) throws BusinessException;

    SearchResult<ProductDto> search(SearchCriteria<ProductSearchCriteria> vmSearchCriteria) throws BusinessException;

    ProductDto update(String id, ProductUpdateModel updateModel) throws BusinessException;

    void upload(MultipartFile file) throws BusinessException;

    ProductViewDto view(String id, ProductViewModel viewModel) throws BusinessException;
}
