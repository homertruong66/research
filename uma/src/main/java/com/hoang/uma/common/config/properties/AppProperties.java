package com.hoang.uma.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * homertruong
 */

@ConfigurationProperties
public class AppProperties {

    private Server     server;
    private Datasource datasource;
    private Redis redis;
    private Logging    logging;

    public Datasource getDatasource () {
        return datasource;
    }

    public void setDatasource (Datasource datasource) {
        this.datasource = datasource;
    }

    public Logging getLogging () {
        return logging;
    }

    public void setLogging (Logging logging) {
        this.logging = logging;
    }

    public Server getServer () {
        return server;
    }

    public void setServer (Server server) {
        this.server = server;
    }

    public Redis getRedis() {
        return redis;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    @ConfigurationProperties(prefix = "server")
    public static class Server {
        private int   port;
        private Jetty jetty;

        public Jetty getJetty () {
            return jetty;
        }

        public void setJetty (Jetty jetty) {
            this.jetty = jetty;
        }

        public int getPort () {
            return port;
        }

        public void setPort (int port) {
            this.port = port;
        }

        @ConfigurationProperties(prefix = "server.jetty")
        public static class Jetty {
            private int queueSize;
            private int minThreads;
            private int maxThreads;
            private int idleTimeout;

            public int getIdleTimeout () {
                return idleTimeout;
            }

            public void setIdleTimeout (int idleTimeout) {
                this.idleTimeout = idleTimeout;
            }

            public int getMaxThreads () {
                return maxThreads;
            }

            public void setMaxThreads (int maxThreads) {
                this.maxThreads = maxThreads;
            }

            public int getMinThreads () {
                return minThreads;
            }

            public void setMinThreads (int minThreads) {
                this.minThreads = minThreads;
            }

            public int getQueueSize () {
                return queueSize;
            }

            public void setQueueSize (int queueSize) {
                this.queueSize = queueSize;
            }
        }
    }

    @ConfigurationProperties(prefix = "datasource")
    public static class Datasource {
        private DatasourceProperties modelsMaster;
        private DatasourceProperties modelsSlave;

        public DatasourceProperties getModelsMaster () {
            return modelsMaster;
        }

        public void setModelsMaster (DatasourceProperties modelsMaster) {
            this.modelsMaster = modelsMaster;
        }

        public DatasourceProperties getModelsSlave () {
            return modelsSlave;
        }

        public void setModelsSlave (DatasourceProperties modelsSlave) {
            this.modelsSlave = modelsSlave;
        }

        public static class DatasourceProperties {
            private String driverClassName;
            private String url;
            private String username;
            private String password;
            private int    initialSize;
            private int    minIdle;
            private int    maxActive;
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

            public int getMaxActive() {
                return maxActive;
            }

            public void setMaxActive(int maxActive) {
                this.maxActive = maxActive;
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

    @ConfigurationProperties(prefix = "redis")
    public static class Redis {
        private String host;
        private int port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    @ConfigurationProperties(prefix = "logging")
    public static class Logging {
        private String file;

        public String getFile () {
            return file;
        }

        public void setFile (String file) {
            this.file = file;
        }
    }

}
