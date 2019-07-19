package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.ShareDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.ShareSearchCriteria;
import com.rms.rms.common.view_model.ShareStatsUpdateModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * homertruong
 */

public interface ShareService {

    String RECENTLY_MODIFIED_SHARE_KEY_SET_ID = "RECENTLY_MODIFIED_SHARE_KEY_SET_ID";
    String RECENTLY_MODIFIED_CLICK_INFO_KEY_SET_ID = "RECENTLY_MODIFIED_CLICK_INFO_KEY_SET_ID";
    String CLICK_INFO_KEY_TEMPLATE = "%s:::%s:::%s:::%s:::%s"; // shareId:::country:::deviceType:::os:::date ( yyyyMMdd )
    String SHARE_KEY_TEMPLATE = "%s:::%s:::%s"; // affiliateId:::url:date( yyyyMMdd )
    String CHANNEL_KEY_TEMPLATE = "CHANNEL_%s";

    Long countClicksByChannelId(String channelId) throws BusinessException;

    List<Map<Date, Integer>> getClicksByDate() throws BusinessException;

    List<Map<Date, Integer>> getClicksByDateByChannelId(String channelId) throws BusinessException;

    List<Map<Date, Integer>> getClicksByDateBySubscriberId(String subscriberId) throws BusinessException;

    SearchResult<ShareDto> search(SearchCriteria<ShareSearchCriteria> vmSearchCriteria, boolean withAssociation) throws BusinessException;

    ShareDto updateStats(ShareStatsUpdateModel statsUpdateModel) throws BusinessException;

    void updateClickCount(String id, int clicks) throws BusinessException;

    void updateClickInfoCount(String clickInfoId, int clicks);

}