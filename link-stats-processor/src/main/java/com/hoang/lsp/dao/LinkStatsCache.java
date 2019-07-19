package com.hoang.lsp.dao;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.core.MetricType;
import com.hoang.lsp.model.LinkStats;


public interface LinkStatsCache {

    public String createHourlyClickKey (Long accountId, BigInteger redirectionId, Calendar createdAt);

    public String createShareMetaDataKey (final Long accountId, final BigInteger redirectionId);

    public Set<String> getHourlyClickKeysByHour (Calendar hour);

    public Set<String> getRecentlyModifiedSet (final MetricType metricType, boolean isHourly);

    public List<LinkStats> getLinkStatsList (final MetricType metricType, Set<String> keySet, boolean isHourly);

    public void increaseClickIncrement (Long accountId, BigInteger redirectionId, Calendar createdAt);

    public void increaseConversionIncrement (Long accountId, BigInteger redirectionId,
                                             Calendar createdAt, GoalType goalType, Long goalValue);

    public void setShareMetaDataToCache (final String key, final String value, boolean override);

    public void updateLastClickedAt (Long accountId, BigInteger redirectionId, Calendar createdAt);

}
