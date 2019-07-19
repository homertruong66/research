package com.rms.rms.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * homertruong
 */

@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private String decimalPattern;
    private String apiPath;
    private ThreadPoolProperties emailThreadPool;
    private String loaderScheduler;
    private int maxFileSize;
    private ThreadPoolProperties taskThreadPool;
    private String version;
    private Boolean useLocalEmailClient;

    public String getDecimalPattern() {
        return decimalPattern;
    }

    public void setDecimalPattern(String decimalPattern) {
        this.decimalPattern = decimalPattern;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public ThreadPoolProperties getEmailThreadPool() {
        return emailThreadPool;
    }

    public void setEmailThreadPool(ThreadPoolProperties emailThreadPool) {
        this.emailThreadPool = emailThreadPool;
    }

    public String getLoaderScheduler() {
        return loaderScheduler;
    }

    public void setLoaderScheduler(String loaderScheduler) {
        this.loaderScheduler = loaderScheduler;
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public ThreadPoolProperties getTaskThreadPool() {
        return taskThreadPool;
    }

    public void setTaskThreadPool(ThreadPoolProperties taskThreadPool) {
        this.taskThreadPool = taskThreadPool;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getUseLocalEmailClient() {
        return useLocalEmailClient;
    }

    public void setUseLocalEmailClient(Boolean useLocalEmailClient) {
        this.useLocalEmailClient = useLocalEmailClient;
    }

    public static class ThreadPoolProperties {

        private int    corePoolSize;
        private int    maxPoolSize;
        private int    idleTimeout;
        private int    queue;
        private String prefix;

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getIdleTimeout() {
            return idleTimeout;
        }

        public void setIdleTimeout(int idleTimeout) {
            this.idleTimeout = idleTimeout;
        }

        public int getQueue() {
            return queue;
        }

        public void setQueue(int queue) {
            this.queue = queue;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}
