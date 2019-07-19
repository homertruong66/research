package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.PostDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PostCreateModel;
import com.rms.rms.common.view_model.PostSearchCriteria;
import com.rms.rms.common.view_model.PostUpdateModel;

/**
 * homertruong
 */

public interface PostService {
    
    PostDto create(PostCreateModel createModel) throws BusinessException;

    void delete(String id) throws BusinessException;

    SearchResult<PostDto> search(SearchCriteria<PostSearchCriteria> vmSearchCriteria) throws BusinessException;

    PostDto update(String id, PostUpdateModel updateModel) throws BusinessException;
}