package com.hoang.lsp.dao;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.core.MetricType;
import com.hoang.lsp.model.LinkStats;

public interface LinkStatsDao {

    public Integer getHourlyClickIncrementingValue(Long accountId, BigInteger redirectionId, String roundedCreatedAt);

    public Integer getHourlyGoalIncrementingValue(Long accountId, BigInteger redirectionId, String roundedCreatedAt, GoalType goalType);
    
    public Integer getHourlyGoalValueIncrementingValue(Long accountId, BigInteger redirectionId, String roundedCreatedAt, GoalType goalType);

    public String getLastClick(Long accountId, BigInteger redirectionId);

    public Integer getTotalsClickIncrementingValue(Long accountId, BigInteger redirectionId);

    public Integer getTotalsGoalIncrementingValue(Long accountId, BigInteger redirectionId, GoalType goalType);

    public Integer getTotalsGoalValueIncrementingValue(Long accountId, BigInteger redirectionId, GoalType goalType);

    public List<LinkStats> queryLinkStatsClicksByHour (Calendar hour);

    public void saveHourlyLinkStatsBatch (MetricType metricType, List<LinkStats> linkStatsList);

    public void saveIncrements (Map<BigInteger, List<LinkStats>> sharesStatsList);

    public void saveTotalsLinkStatsBatch (MetricType metricType, List<LinkStats> linkStatsList);

}
