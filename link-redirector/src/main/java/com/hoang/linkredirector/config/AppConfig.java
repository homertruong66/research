package com.hoang.linkredirector.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hoang.linkredirector.config.properties.AppProperties;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {

    @Autowired
    AppProperties appProperties;

    @Bean
    public EmbeddedServletContainerFactory servletContainer () {
        AppProperties.Server.Jetty jettyProperties = appProperties.getServer().getJetty();
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(jettyProperties.getQueueSize());
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(
            jettyProperties.getMaxThreads(),
            jettyProperties.getMinThreads(),
            jettyProperties.getIdleTimeout(),
            queue
        );
        JettyEmbeddedServletContainerFactory jetty = new JettyEmbeddedServletContainerFactory();
        jetty.setThreadPool(queuedThreadPool);
        return jetty;
    }
}
