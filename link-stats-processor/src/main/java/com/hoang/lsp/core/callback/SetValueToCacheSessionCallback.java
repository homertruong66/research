package com.hoang.lsp.core.callback;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;

public class SetValueToCacheSessionCallback implements SessionCallback<List<Object>> {

    private final String key;
    private final String value;
    private final long expire;
    private boolean override = false;

    public SetValueToCacheSessionCallback(String key, String value, long expire) {
        this.key = key;
        this.value = value;
        this.expire = expire;
    }

    public SetValueToCacheSessionCallback(String key, String value, long expire, boolean override) {
        this(key, value, expire);
        this.override = override;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public List<Object> execute(RedisOperations operations) throws DataAccessException {
        operations.multi();
        if (this.override) {
            operations.opsForValue().set(this.key, this.value);
        } else {
            operations.opsForValue().setIfAbsent(this.key, this.value);
        }
        operations.expire(this.key, this.expire, TimeUnit.SECONDS);
        return operations.exec();
    }

}
