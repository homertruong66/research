package com.hoang.lsp.core.callback;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;

import com.hoang.lsp.dao.LinkStatsCacheImpl;

public class ClickIncrementSessionCallback implements SessionCallback<List<Object>> {

    private final String hourlyKey;
    private final String totalsKey;
    private long hourlyClickExpire;
    private long totalsClickExpire;

    public ClickIncrementSessionCallback (String hourlyKey, String totalsKey, long hourlyClickExpire, long totalsClickExpire) {
        this.hourlyKey = hourlyKey;
        this.totalsKey = totalsKey;
        this.hourlyClickExpire = hourlyClickExpire;
        this.totalsClickExpire = totalsClickExpire;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public List<Object> execute(RedisOperations operations) throws DataAccessException {
        operations.multi();
        operations.opsForValue().increment(this.hourlyKey, 1L);
        operations.expire(this.hourlyKey, this.hourlyClickExpire, TimeUnit.SECONDS);
        operations.opsForSet().add(LinkStatsCacheImpl.HOURLY_CLICKS_RECENTLY_MODIFIED_KEY, this.hourlyKey);

        operations.opsForValue().increment(this.totalsKey, 1L);
        operations.expire(this.totalsKey, this.totalsClickExpire, TimeUnit.SECONDS);
        operations.opsForSet().add(LinkStatsCacheImpl.TOTALS_CLICKS_RECENTLY_MODIFIED_KEY, this.totalsKey);
        return operations.exec();
    }

}
