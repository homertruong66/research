package com.hoang.lsp.core.callback;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;

public class ConversionHourlyAndTotalsIncrementSessionCallback implements SessionCallback<List<Object>> {

    private final String goalHourlyKey;
    private final String goalTotalsKey;
    private final String goalValueHourlyKey;
    private final String goalValueTotalsKey;
    private final String hourlyRecentlyModifiedKey;
    private final String totalsRecentlyModifiedKey;
    private final Long goalValue;
    private long hourlyConversionExpire;
    private long totalsConversionExpire;

    public ConversionHourlyAndTotalsIncrementSessionCallback(String goalHourlyKey, String goalTotalsKey, String goalValueHourlyKey, String goalValueTotalsKey,
            String hourlyRecentlyModifiedKey, String totalsRecentlyModifiedKey, Long goalValue, long hourlyConversionExpire, long totalsConversionExpire) {
        this.goalHourlyKey = goalHourlyKey;
        this.goalTotalsKey = goalTotalsKey;
        this.goalValueHourlyKey = goalValueHourlyKey;
        this.goalValueTotalsKey = goalValueTotalsKey;
        this.hourlyRecentlyModifiedKey = hourlyRecentlyModifiedKey;
        this.totalsRecentlyModifiedKey = totalsRecentlyModifiedKey;
        this.goalValue = goalValue;
        this.hourlyConversionExpire = hourlyConversionExpire;
        this.totalsConversionExpire = totalsConversionExpire;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<Object> execute(RedisOperations operations) throws DataAccessException {
        operations.multi();
        operations.opsForValue().increment(this.goalHourlyKey, 1L);
        operations.expire(this.goalHourlyKey, this.hourlyConversionExpire, TimeUnit.SECONDS);
        operations.opsForSet().add(this.hourlyRecentlyModifiedKey, this.goalHourlyKey);

        operations.opsForValue().increment(this.goalTotalsKey, 1L);
        operations.expire(this.goalTotalsKey, this.totalsConversionExpire, TimeUnit.SECONDS);
        operations.opsForSet().add(this.totalsRecentlyModifiedKey, this.goalTotalsKey);

        if (this.goalValueHourlyKey != null && this.goalValueTotalsKey != null && this.goalValue != null && this.goalValue > 0) {
            operations.opsForValue().increment(this.goalValueHourlyKey, this.goalValue);
            operations.expire(this.goalValueHourlyKey, this.hourlyConversionExpire, TimeUnit.SECONDS);

            operations.opsForValue().increment(this.goalValueTotalsKey, this.goalValue);
            operations.expire(this.goalValueTotalsKey, this.totalsConversionExpire, TimeUnit.SECONDS);
        }
        return operations.exec();
    }

}
