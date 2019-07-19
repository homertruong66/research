package com.rms.rms.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * homertruong
 */

@ConfigurationProperties(prefix = "database-server")
public class DatabaseServerProperties {

    private MysqlProperties mysql;

    public MysqlProperties getMysql() {
        return mysql;
    }

    public void setMysql(MysqlProperties mysql) {
        this.mysql = mysql;
    }


    @ConfigurationProperties(prefix = "database-server.mysql")
    public static class MysqlProperties {

        private String driverClassName;
        private String url;
        private String username;
        private String password;
        private int    initialSize;
        private int    minIdle;
        private int    maximumPoolSize;
        private boolean showSql;
        private int batchSize;

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getInitialSize() {
            return initialSize;
        }

        public void setInitialSize(int initialSize) {
            this.initialSize = initialSize;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaximumPoolSize() {
            return maximumPoolSize;
        }

        public void setMaximumPoolSize(int maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
        }

        public boolean isShowSql() {
            return showSql;
        }

        public void setShowSql(boolean showSql) {
            this.showSql = showSql;
        }

        public int getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }
    }
}