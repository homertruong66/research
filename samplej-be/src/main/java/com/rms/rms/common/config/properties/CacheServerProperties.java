package com.rms.rms.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * homertruong
 */

@ConfigurationProperties(prefix = "cache-server")
public class CacheServerProperties {

    private int acquireLockSleep;
    private int clickExpired;
    private int lockKeyExpired;
    private RedisProperties redis;

    @ConfigurationProperties(prefix = "cache-server.redis")
    public static class RedisProperties {

        private boolean blockWhenExhausted;
        private int defaultExpired;
        private String host;
        private int maxTotal;
        private int minIdle;
        private String password;
        private int port;
        private int timeout;

        public int getDefaultExpired() {
            return defaultExpired;
        }

        public void setDefaultExpired(int defaultExpired) {
            this.defaultExpired = defaultExpired;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public boolean isBlockWhenExhausted() {
            return blockWhenExhausted;
        }

        public void setBlockWhenExhausted(boolean blockWhenExhausted) {
            this.blockWhenExhausted = blockWhenExhausted;
        }

    }

    public int getAcquireLockSleep () {
        return acquireLockSleep;
    }

    public void setAcquireLockSleep (int acquireLockSleep) {
        this.acquireLockSleep = acquireLockSleep;
    }

    public int getLockKeyExpired() {
        return lockKeyExpired;
    }

    public void setLockKeyExpired(int lockKeyExpired) {
        this.lockKeyExpired = lockKeyExpired;
    }

    public RedisProperties getRedis() {
        return redis;
    }

    public void setRedis(RedisProperties redis) {
        this.redis = redis;
    }

    public int getClickExpired() {
        return clickExpired;
    }

    public void setClickExpired(int clickExpired) {
        this.clickExpired = clickExpired;
    }

}