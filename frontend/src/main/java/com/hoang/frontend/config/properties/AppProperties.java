package com.hoang.frontend.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class AppProperties {

    private Server     server;
    private Logging    logging;

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
