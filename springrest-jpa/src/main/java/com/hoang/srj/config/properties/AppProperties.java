package com.hoang.srj.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class AppProperties {

    private Server     server;
    private Datasource datasource;
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

            public String getDriverClassName () {
                return driverClassName;
            }

            public void setDriverClassName (String driverClassName) {
                this.driverClassName = driverClassName;
            }

            public int getInitialSize () {
                return initialSize;
            }

            public void setInitialSize (int initialSize) {
                this.initialSize = initialSize;
            }

            public int getMaxActive () {
                return maxActive;
            }

            public void setMaxActive (int maxActive) {
                this.maxActive = maxActive;
            }

            public int getMinIdle () {
                return minIdle;
            }

            public void setMinIdle (int minIdle) {
                this.minIdle = minIdle;
            }

            public String getPassword () {
                return password;
            }

            public void setPassword (String password) {
                this.password = password;
            }

            public String getUrl () {
                return url;
            }

            public void setUrl (String url) {
                this.url = url;
            }

            public String getUsername () {
                return username;
            }

            public void setUsername (String username) {
                this.username = username;
            }
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
