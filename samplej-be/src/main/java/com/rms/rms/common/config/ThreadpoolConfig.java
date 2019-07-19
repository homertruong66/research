package com.rms.rms.common.config;

import com.rms.rms.common.config.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.*;

/**
 * homertruong
 */

@Configuration
public class ThreadpoolConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    private ExecutorService getExecutor(ApplicationProperties.ThreadPoolProperties properties) {
        ThreadFactory threadFactory = new ThreadPoolExecutorFactoryBean() {
            {
                setThreadNamePrefix(properties.getPrefix());
            }
        };

        return new ThreadPoolExecutor(
            properties.getCorePoolSize(),
            properties.getMaxPoolSize(),
            properties.getIdleTimeout(),
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(properties.getQueue()),
            threadFactory
        );
    }

    @Bean("emailExecutorService")
    public ExecutorService emailExecutorService() {
        ApplicationProperties.ThreadPoolProperties threadPoolProperties = applicationProperties.getEmailThreadPool();
        threadPoolProperties.setPrefix("emailWorkerThreadPool-");
        return this.getExecutor(threadPoolProperties);
    }

    @Bean("taskExecutorService")
    public ExecutorService taskExecutorService() {
        ApplicationProperties.ThreadPoolProperties threadPoolProperties = applicationProperties.getTaskThreadPool();
        threadPoolProperties.setPrefix("taskWorkerThreadPool-");
        return this.getExecutor(threadPoolProperties);
    }

}
