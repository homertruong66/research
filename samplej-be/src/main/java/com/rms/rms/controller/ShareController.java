package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.dto.ShareDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.ShareSearchCriteria;
import com.rms.rms.common.view_model.ShareStatsUpdateModel;
import com.rms.rms.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * homertruong
 */
public class ShareController {

    @Autowired
    private ShareService shareService;

    public ResponseDto search(SearchCriteria<ShareSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<ShareDto> dtoSearchResult = shareService.search(vmSearchCriteria, true);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto updateStats(ShareStatsUpdateModel statsUpdateModel) throws BusinessException {
        ShareDto dto = shareService.updateStats(statsUpdateModel);
        return new ResponseDto(dto);
    }

}
