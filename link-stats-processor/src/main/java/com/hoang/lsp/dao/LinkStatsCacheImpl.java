package com.hoang.lsp.dao;

import static com.hoang.lsp.model.Constant.KEY_SEPARATOR;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.core.MetricType;
import com.hoang.lsp.core.callback.ClickIncrementSessionCallback;
import com.hoang.lsp.core.callback.ConversionHourlyAndTotalsIncrementSessionCallback;
import com.hoang.lsp.core.callback.SetValueToCacheSessionCallback;
import com.hoang.lsp.model.Constant;
import com.hoang.lsp.model.LinkStats;
import com.hoang.lsp.utils.ConvertUtils;
import com.hoang.lsp.utils.DateTimeUtils;

@Repository
public class LinkStatsCacheImpl implements LinkStatsCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkStatsCacheImpl.class);

    private static final String FIRST_KEY_SEPARATOR = "_";
    private static final String REPROCESS_PREFIX    = "reprocess";

    public static final  String HOURLY_CLICKS_RECENTLY_MODIFIED_KEY          = Constant.HOURLY_PREFIX + KEY_SEPARATOR + "clicks" + KEY_SEPARATOR + "recently_modified";
    public static final  String TOTALS_CLICKS_RECENTLY_MODIFIED_KEY          = Constant.TOTALS_PREFIX + KEY_SEPARATOR + "clicks" + KEY_SEPARATOR + "recently_modified";
    private static final String TOTALS_LAST_CLICKED_AT_RECENTLY_MODIFIED_KEY = Constant.TOTALS_PREFIX + FIRST_KEY_SEPARATOR + "last_clicked_at" + KEY_SEPARATOR + "recently_modified";

    private static final String HOURLY_CONVERSION_RECENTLY_MODIFIED_KEY_TEMPLATE = Constant.HOURLY_PREFIX + FIRST_KEY_SEPARATOR + "{0}" + KEY_SEPARATOR + "recently_modified";
    private static final String TOTALS_CONVERSION_RECENTLY_MODIFIED_KEY_TEMPLATE = Constant.TOTALS_PREFIX + FIRST_KEY_SEPARATOR + "{0}" + KEY_SEPARATOR + "recently_modified";

    @Autowired
    private LinkStatsDao linkStatsDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${redis.hourly.clicks.expire}")
    private long hourlyClickExpire;

    @Value("${redis.hourly.conversion.expire}")
    private long hourlyConversionExpire;

    @Value("${redis.totals.lock.expire}")
    private long totalsLockExpire;

    @Value("${redis.id.lock.wait}")
    private long idLockWait;

    @Value("${redis.totals.clicks.expire}")
    private long totalsClickExpire;

    @Value("${redis.totals.lastclicks.expire}")
    private long lastClickExpire;

    @Value("${redis.totals.conversion.expire}")
    private long totalsConversionExpire;

    @Value("${redis.redirections.element.expire}")
    private long expire;

    private static final String SHARE_META_KEY_TEMPLATE = "metadata::{0}::{1}";

    @Override
    public String createHourlyClickKey (Long accountId, BigInteger redirectionId, Calendar createdAt) {
        return Constant.HOURLY_PREFIX + FIRST_KEY_SEPARATOR + "clicks" + KEY_SEPARATOR + accountId + KEY_SEPARATOR + DateTimeUtils.roundTimestampToEarliestHour(createdAt)
               + KEY_SEPARATOR + redirectionId;
    }

    @Override
    public String createShareMetaDataKey (final Long accountId, final BigInteger redirectionId) {
        return MessageFormat.format(SHARE_META_KEY_TEMPLATE, accountId, redirectionId.toString());
    }

    @Override
    public Set<String> getHourlyClickKeysByHour (final Calendar hour) {
        final String wildCardHourlyKey = createWildcardHourlyClickKey(hour);
        return this.redisTemplate.keys(wildCardHourlyKey);
    }

    @Override
    public Set<String> getRecentlyModifiedSet (final MetricType metricType, boolean isHourly) {
        String hourlyRecentlyModifiedKey = buildRecentlyModifiedKey(metricType, isHourly);
        // We don't need to remove the set here! The lock on Link-Id/Link-Id-Goal will handle this
        final Set<String> members = redisTemplate.opsForSet().members(hourlyRecentlyModifiedKey);
        return standardizeSet(members);
    }

    @Override
    public List<LinkStats> getLinkStatsList (final MetricType metricType, Set<String> keySet, boolean isHourly) {
        final List<LinkStats> linkStatsList = new ArrayList<>();
        for (String key : keySet) {
            LinkStats linkStats = getLinkStats(metricType, key, isHourly);
            if ( linkStats == null ) {
                LOGGER.debug("ignore key: " + key + " with no value");
                continue;
            }
            else {
                LOGGER.debug("processing key: " + key + " with value: " + linkStats.toString());
            }
            linkStatsList.add(linkStats);
        }
        return linkStatsList;
    }

    @Override
    public void increaseClickIncrement (final Long accountId, final BigInteger redirectionId, final Calendar createdAt) {
        final String hourlyKey = createHourlyClickKey(accountId, redirectionId, createdAt);
        final String totalsKey = createTotalsClickKey(accountId, redirectionId);

        // acquire the lock
        String randomValue = Math.random() + "";
        while ( true ) {
            LOGGER.debug("Click Processor acquiring recently modified lock (" + randomValue + ") ...");
            if ( acquireRecentlyModifiedIdLock(redirectionId.toString(), randomValue) ) {
                break;
            }
            LOGGER.debug("Click Processor waiting for recently modified lock (" + randomValue + ") ...");
            Thread.currentThread();
            try {
                Thread.sleep(idLockWait);
            }
            catch (InterruptedException e) {
            }
        }

        // do the job
        LOGGER.info("Click Processor acquired lock, then increasing hourly and total clicks...");
        redisTemplate.executePipelined(new ClickIncrementSessionCallback(hourlyKey, totalsKey, this.hourlyClickExpire, this.totalsClickExpire));

        // release the lock
        LOGGER.debug("Click Processor releasing recently modified lock (" + randomValue + ") ...");
        releaseRecentlyModifiedIdLock(redirectionId.toString(), randomValue);
    }

    @Override
    public void increaseConversionIncrement (final Long accountId, final BigInteger redirectionId,
                                             final Calendar createdAt, final GoalType goalType, final Long goalValue) {
        final String hourlyGoalKey = createHourlyGoalKey(accountId, redirectionId, createdAt, goalType);
        final String totalsGoalKey = createTotalsGoalKey(accountId, redirectionId, goalType);

        String goalValueHourlyKey = null;
        String goalValueTotalsKey = null;
        if ( goalValue != null ) {
            goalValueHourlyKey = createHourlyGoalValueKey(accountId, redirectionId, createdAt, goalType);
            goalValueTotalsKey = createTotalsGoalValueKey(accountId, redirectionId, goalType);
        }

        final String hourlyRecentlyModifiedKey = createHourlyConversionRecentlyModifiedKey(goalType);
        final String totalsRecentlyModifiedKey = createTotalsConversionRecentlyModifiedKey(goalType);

        final String lockKey = redirectionId.toString() + KEY_SEPARATOR + goalType.getValue();
        // acquire the lock
        String randomValue = Math.random() + "";
        while ( true ) {
            LOGGER.debug("Conversion Processor acquiring recently modified lock (" + randomValue + ") ...");
            if ( acquireRecentlyModifiedIdLock(lockKey, randomValue) ) {
                break;
            }
            LOGGER.debug("Conversion Processor waiting for recently modified lock (" + randomValue + ") ...");
            Thread.currentThread();
            try {
                Thread.sleep(idLockWait);
            }
            catch (InterruptedException e) {
            }
        }

        // do the job
        LOGGER.info("Conversion Processor acquired lock, then increasing hourly and total goals...");
        redisTemplate.executePipelined(new ConversionHourlyAndTotalsIncrementSessionCallback(hourlyGoalKey, totalsGoalKey, goalValueHourlyKey, goalValueTotalsKey,
                                                                                             hourlyRecentlyModifiedKey, totalsRecentlyModifiedKey, goalValue, this.hourlyConversionExpire, this.totalsConversionExpire
        ));

        // release the lock
        LOGGER.debug("Conversion Processor releasing hourly recently modified lock (" + randomValue + ") ...");
        releaseRecentlyModifiedIdLock(lockKey, randomValue);
    }

    @Override
    public void setShareMetaDataToCache (final String key, final String value, boolean override) {
        redisTemplate.executePipelined(new SetValueToCacheSessionCallback(key, value, this.expire, override));
    }

    @Override
    public void updateLastClickedAt (final Long accountId, final BigInteger redirectionId, final Calendar createdAt) {
        final String lastClickKey = createLastClickAtKey(accountId, redirectionId);
        confirmLastClickCache(lastClickKey, accountId, redirectionId);

        setValueToCache(lastClickKey, (createdAt.getTimeInMillis() / 1000L) + "", this.lastClickExpire, true);
        addToRecentlyModifiedSet(TOTALS_LAST_CLICKED_AT_RECENTLY_MODIFIED_KEY, lastClickKey);
    }


    // //// Utilities //////
    private boolean acquireRecentlyModifiedIdLock (String idLock, String randomValue) {
        boolean acquired = false;
        String lockKey = "lock." + idLock;
        if ( redisTemplate.opsForValue().setIfAbsent(lockKey, randomValue) ) {
            redisTemplate.opsForValue().set(lockKey, randomValue, this.totalsLockExpire, TimeUnit.SECONDS);
            acquired = true;
        }

        return acquired;
    }

    private void addToRecentlyModifiedSet (final String recentlyModifiedSetKey, final String... keys) {
        redisTemplate.opsForSet().add(recentlyModifiedSetKey, keys);
    }

    private String buildRecentlyModifiedKey (MetricType metricType, boolean isHourly) {
        String key = null;
        switch (metricType) {
            case CLICK:
                if ( isHourly ) {
                    key = HOURLY_CLICKS_RECENTLY_MODIFIED_KEY;
                }
                else {
                    key = TOTALS_CLICKS_RECENTLY_MODIFIED_KEY;
                }
                break;
            case LAST_CLICKED_AT:
                if ( isHourly ) {
                    throw new IllegalArgumentException("HourlyRecentlyModifiedSet does not apply for lastClickedAt metric");
                }
                else {
                    key = TOTALS_LAST_CLICKED_AT_RECENTLY_MODIFIED_KEY;
                    break;
                }
            case PAGE_VIEW:
            case GOAL_1:
            case GOAL_2:
            case GOAL_3:
            case GOAL_4:
            case GOAL_5:
            case GOAL_6:
            case GOAL_7:
            case GOAL_8:
            case GOAL_9:
            case GOAL_10:
                GoalType goalType = ConvertUtils.convertToGoalType(metricType);
                if ( isHourly ) {
                    key = createHourlyConversionRecentlyModifiedKey(goalType);
                }
                else {
                    key = createTotalsConversionRecentlyModifiedKey(goalType);
                }
                break;

            default:
                throw new IllegalArgumentException("metricType is invalid:" + metricType);
        }
        return key;
    }

    private void confirmClickCachedTotals (final String clickTotalsCacheKey, final Long accountId, final BigInteger redirectionId) {
        final String valueFromCache = redisTemplate.opsForValue().get(clickTotalsCacheKey);
        if ( valueFromCache == null ) {
            Integer valueFromDb = linkStatsDao.getTotalsClickIncrementingValue(accountId, redirectionId);
            Integer cacheValue = 0;
            if ( valueFromDb != null ) {
                cacheValue = valueFromDb;
            }
            setValueToCache(clickTotalsCacheKey, cacheValue.toString(), this.totalsClickExpire, false);
        }
    }

    private void confirmLastClickCache (final String lastClickKey, final Long accountId, final BigInteger redirectionId) {
        final String valueFromCache = redisTemplate.opsForValue().get(lastClickKey);
        if ( valueFromCache == null ) {
            String valueFromDb = linkStatsDao.getLastClick(accountId, redirectionId);
            if ( valueFromDb != null ) {
                setValueToCache(lastClickKey, valueFromDb, this.lastClickExpire, false);
            }
        }
    }

    private String createHourlyConversionRecentlyModifiedKey (final GoalType goalType) {
        return MessageFormat.format(HOURLY_CONVERSION_RECENTLY_MODIFIED_KEY_TEMPLATE, goalType.getValue());
    }

    private String createHourlyGoalKey (Long accountId, BigInteger redirectionId, Calendar createdAt, GoalType goalType) {
        String roundTimestamp = DateTimeUtils.roundTimestampToEarliestHour(createdAt);
        return Constant.HOURLY_PREFIX + KEY_SEPARATOR + goalType.getValue() + KEY_SEPARATOR + accountId + KEY_SEPARATOR + roundTimestamp + KEY_SEPARATOR + redirectionId;
    }

    private String createHourlyGoalValueKey (Long accountId, BigInteger redirectionId, Calendar createdAt, GoalType goalType) {
        String roundTimestamp = DateTimeUtils.roundTimestampToEarliestHour(createdAt);
        return Constant.HOURLY_PREFIX + KEY_SEPARATOR + getGoalLabelValue(goalType) + KEY_SEPARATOR + accountId + KEY_SEPARATOR + roundTimestamp + KEY_SEPARATOR + redirectionId;
    }

    private String createLastClickAtKey (Long accountId, BigInteger redirectionId) {
        return Constant.TOTALS_PREFIX + FIRST_KEY_SEPARATOR + "last_clicked_at" + KEY_SEPARATOR + accountId + KEY_SEPARATOR + redirectionId;
    }

    private String createReprocessedHourlyClickKey (Long accountId, BigInteger redirectionId, Calendar createdAt) {
        return REPROCESS_PREFIX + KEY_SEPARATOR + Constant.HOURLY_PREFIX + KEY_SEPARATOR + "clicks" + KEY_SEPARATOR + accountId + KEY_SEPARATOR
               + DateTimeUtils.roundTimestampToEarliestHour(createdAt) + KEY_SEPARATOR + redirectionId;
    }

    private String createTotalsClickKey (Long accountId, BigInteger redirectionId) {
        return Constant.TOTALS_PREFIX + FIRST_KEY_SEPARATOR + "clicks" + KEY_SEPARATOR + accountId + KEY_SEPARATOR + redirectionId;
    }

    private String createTotalsConversionRecentlyModifiedKey (final GoalType goalType) {
        return MessageFormat.format(TOTALS_CONVERSION_RECENTLY_MODIFIED_KEY_TEMPLATE, goalType.getValue());
    }

    private String createTotalsGoalKey (Long accountId, BigInteger redirectionId, GoalType goalType) {
        return Constant.TOTALS_PREFIX + KEY_SEPARATOR + goalType.getValue() + KEY_SEPARATOR + accountId + KEY_SEPARATOR + redirectionId;
    }

    private String createTotalsGoalValueKey (Long accountId, BigInteger redirectionId, GoalType goalType) {
        return Constant.TOTALS_PREFIX + KEY_SEPARATOR + getGoalLabelValue(goalType) + KEY_SEPARATOR + accountId + KEY_SEPARATOR + redirectionId;
    }

    private String createWildcardHourlyClickKey (final Calendar hour) {
        String wildCardHourlyKey =
            Constant.HOURLY_PREFIX + KEY_SEPARATOR + "clicks" + KEY_SEPARATOR + "*" + KEY_SEPARATOR + DateTimeUtils.roundTimestampToEarliestHour(hour) + KEY_SEPARATOR + "*";
        return wildCardHourlyKey;
    }

    private String getCachedValueAndRemoveKeyFromSet (final String key, String lockKey, String setKey) {
        String result = StringUtils.EMPTY;
        LOGGER.info("Loader acquired lock, then set the value of current key = 0");
        // acquiring the lock
        String randomValue = Math.random() + "";
        while ( true ) {
            LOGGER.debug("Loader acquiring recently modified lock (" + randomValue + ") ...");
            if ( StringUtils.isEmpty(lockKey) || acquireRecentlyModifiedIdLock(lockKey, randomValue) ) {
                break;
            }
            LOGGER.debug("Loader waiting for recently modified lock (" + randomValue + ") ...");
            Thread.currentThread();
            try {
                Thread.sleep(idLockWait);
            }
            catch (InterruptedException e) {
            }
        }
        result = redisTemplate.opsForValue().getAndSet(key, "0");
        redisTemplate.opsForSet().remove(setKey, key);

        // release the lock
        LOGGER.debug("Loader releasing recently modified lock (" + randomValue + ") ...");
        releaseRecentlyModifiedIdLock(lockKey, randomValue);
        return result;
    }

    private String getGoalLabelValue (final GoalType goalType) {
        return goalType.getValue() + "_value";
    }

    private LinkStats getLinkStats (final MetricType metricType, String key, boolean isHourly) {
        int redirectionIdIndex = 3;
        int accountIdIndex = 2;
        int hourIndex = 3;
        LinkStats linkStats = new LinkStats();
        String tempKey = key;
        String recentlyModifiedKey = buildRecentlyModifiedKey(metricType, isHourly);
        try {
            if ( !key.contains("goal") ) {
                tempKey = key.replaceFirst(FIRST_KEY_SEPARATOR, KEY_SEPARATOR);
            }
            String[] tokens = tempKey.split(KEY_SEPARATOR);

            Calendar hour = null;
            if ( isHourly ) {
                redirectionIdIndex = 4;
                hour = DateTimeUtils.toCalendar(tokens[hourIndex]);
                linkStats.setHour(hour);
            }

            Long accountId = Long.parseLong(tokens[accountIdIndex]);
            BigInteger redirectionId = new BigInteger(tokens[redirectionIdIndex]);
            linkStats.setAccountId(accountId);
            linkStats.setRedirectionId(redirectionId);

            String lastClickAtFromCache = redisTemplate.opsForValue().get(createLastClickAtKey(accountId, redirectionId));
            String clickedDate = DateTimeUtils.parseCalendarWithoutTime(lastClickAtFromCache);
            switch (metricType) {
                case CLICK:
                    Integer clickIncrement = NumberUtils.toInt(getCachedValueAndRemoveKeyFromSet(key, tokens[redirectionIdIndex], recentlyModifiedKey), 0);
                    linkStats.setClicksIncrement(clickIncrement);
                    linkStats.setClickedDate(clickedDate);
                    break;
                case LAST_CLICKED_AT:
                    if ( lastClickAtFromCache != null ) {
                        linkStats.setLastClickedAt(DateTimeUtils.toCalendarWithSecondTime(lastClickAtFromCache));
                    }
                    else {
                        return null;
                    }
                    break;
                case PAGE_VIEW:
                case GOAL_1:
                case GOAL_2:
                case GOAL_3:
                case GOAL_4:
                case GOAL_5:
                case GOAL_6:
                case GOAL_7:
                case GOAL_8:
                case GOAL_9:
                case GOAL_10:
                    GoalType goalType = GoalType.parse(tokens[1]);
                    final String goalIdLockKey = redirectionId.toString() + KEY_SEPARATOR + goalType.getValue();
                    String goalValueKey;
                    if ( isHourly ) {
                        goalValueKey = createHourlyGoalValueKey(accountId, redirectionId, hour, goalType);
                    }
                    else {
                        goalValueKey = createTotalsGoalValueKey(accountId, redirectionId, goalType);
                    }

                    String goalFromCache = getCachedValueAndRemoveKeyFromSet(key, goalIdLockKey, recentlyModifiedKey);
                    Integer goal = 0;
                    if ( goalFromCache != null ) {
                        goal = NumberUtils.toInt(goalFromCache, 0);
                    }

                    String goalValueFromCache = getCachedValueAndRemoveKeyFromSet(goalValueKey, goalIdLockKey, recentlyModifiedKey);
                    Integer goalValue = 0;
                    if ( goalValueFromCache != null ) {
                        goalValue = NumberUtils.toInt(goalValueFromCache, 0);
                    }
                    linkStats.setClickedDate(clickedDate);
                    setGoalIncrementToLinkStats(linkStats, goalType, goal, goalValue);
                    break;

                default:
                    break;
            }
            return linkStats;
        }
        catch (Exception ex) {
            LOGGER.error("Failed to get LinkStats", ex);
            return null;
        }
    }

    private LinkStats getLinkStatsByReprocessedHourlyClickKey (final String reprocessedClickHourlyKey) {
        int accountIdIndex = 3;
        int hourIndex = 4;
        int redirectionIdIndex = 5;
        LinkStats linkStats = new LinkStats();

        try {
            String[] tokens = reprocessedClickHourlyKey.split(KEY_SEPARATOR);

            Long accountId = Long.parseLong(tokens[accountIdIndex]);
            Calendar hour = DateTimeUtils.toCalendar(tokens[hourIndex]);
            BigInteger redirectionId = new BigInteger(tokens[redirectionIdIndex]);
            linkStats.setAccountId(accountId);
            linkStats.setHour(hour);
            linkStats.setRedirectionId(redirectionId);
            // TODO
            // Need to check the logic of Reprocessor and modify the code in future
            String reprocessedClickHourlyValue = redisTemplate.opsForValue().get(reprocessedClickHourlyKey);
            if ( reprocessedClickHourlyValue != null ) {
                linkStats.setClicksIncrement(NumberUtils.toInt(reprocessedClickHourlyValue, 0));
            }
            return linkStats;
        }
        catch (Exception ex) {
            LOGGER.error("Failed to create LinkStats", ex);
            return null;
        }
    }

    private void releaseRecentlyModifiedIdLock (String idLock, String randomValue) {
        String lockKey = "lock." + idLock;
        String rv = redisTemplate.opsForValue().get(lockKey);
        if ( rv != null && rv.equals(randomValue) ) {
            redisTemplate.delete(lockKey);
        }
    }

    private void setValueToCache (final String key, final String value, final long expire, final boolean override) {
        redisTemplate.executePipelined(new SetValueToCacheSessionCallback(key, value, expire, override));
    }

    private void setGoalIncrementToLinkStats (final LinkStats linkStats, GoalType goalType, Integer goal, Integer goalValue) {
        switch (goalType) {
            case PAGE_VIEW:
                linkStats.setPageViewsIncrement(goal);
                break;
            case GOAL_1:
                linkStats.setGoal1Increment(goal);
                linkStats.setGoal1ValueIncrement(goalValue);
                break;
            case GOAL_2:
                linkStats.setGoal2Increment(goal);
                linkStats.setGoal2ValueIncrement(goalValue);
                break;
            case GOAL_3:
                linkStats.setGoal3Increment(goal);
                linkStats.setGoal3ValueIncrement(goalValue);
                break;
            case GOAL_4:
                linkStats.setGoal4Increment(goal);
                linkStats.setGoal4ValueIncrement(goalValue);
                break;
            case GOAL_5:
                linkStats.setGoal5Increment(goal);
                linkStats.setGoal5ValueIncrement(goalValue);
                break;
            case GOAL_6:
                linkStats.setGoal6Increment(goal);
                linkStats.setGoal6ValueIncrement(goalValue);
                break;
            case GOAL_7:
                linkStats.setGoal7Increment(goal);
                linkStats.setGoal7ValueIncrement(goalValue);
                break;
            case GOAL_8:
                linkStats.setGoal8Increment(goal);
                linkStats.setGoal8ValueIncrement(goalValue);
                break;
            case GOAL_9:
                linkStats.setGoal9Increment(goal);
                linkStats.setGoal9ValueIncrement(goalValue);
                break;
            case GOAL_10:
                linkStats.setGoal10Increment(goal);
                linkStats.setGoal10ValueIncrement(goalValue);
                break;

            default:
                break;
        }
    }

    private Set<String> standardizeSet (final Set<String> collections) {
        if ( collections == null ) {
            return Collections.emptySet();
        }
        return collections;
    }

}
