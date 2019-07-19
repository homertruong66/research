package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ChannelDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.ChannelCreateModel;
import com.rms.rms.common.view_model.ChannelSearchCriteria;
import com.rms.rms.common.view_model.ChannelUpdateModel;

/**
 * homertruong
 */

public interface ChannelService {
    
    ChannelDto create(ChannelCreateModel createModel) throws BusinessException;

    ChannelDto get(String id) throws BusinessException;

    SearchResult<ChannelDto> search(SearchCriteria<ChannelSearchCriteria> vmSearchCriteria) throws BusinessException;

    ChannelDto update(String id, ChannelUpdateModel updateModel) throws BusinessException;
    
}