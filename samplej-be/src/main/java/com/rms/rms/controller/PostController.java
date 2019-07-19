package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.PostDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.PostCreateModel;
import com.rms.rms.common.view_model.PostSearchCriteria;
import com.rms.rms.common.view_model.PostUpdateModel;
import com.rms.rms.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class PostController {

    @Autowired
    private PostService postService;

    public ResponseDto create(PostCreateModel createModel) throws BusinessException {
        PostDto dto = postService.create(createModel);
        return new ResponseDto(dto);
    }

    public void delete(String id) throws BusinessException {
        postService.delete(id);
    }

    public ResponseDto search(SearchCriteria<PostSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<PostDto> dtoSearchResult = postService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto update(String id, PostUpdateModel updateModel) throws BusinessException {
        PostDto dto = postService.update(id, updateModel);
        return new ResponseDto(dto);
    }

}
