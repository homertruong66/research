package com.rms.rms.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * homertruong
 */

@ConfigurationProperties(prefix = "web-server")
public class WebServerProperties {

    private int   port;
    private JettyProperties jetty;

    public int getPort () {
        return port;
    }

    public void setPort (int port) {
        this.port = port;
    }

    public JettyProperties getJetty () {
        return jetty;
    }

    public void setJetty (JettyProperties jetty) {
        this.jetty = jetty;
    }


    @ConfigurationProperties(prefix = "web-server.jetty")
    public static class JettyProperties {

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