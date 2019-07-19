package com.hoang.lsp.loader;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.task.ConversionLoaderTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

@Component
public class ConversionLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversionLoader.class);

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
        11,
        new ThreadFactoryBuilder().
            setPriority(Thread.NORM_PRIORITY + 1).
            setNameFormat("ConversionLoaderPool-Thread-%d").
            build()
    );

    @Value("${loader.conversion.disabled}")
    private int disabled;

    @Value("${loader.initial.delay}")
    private int initialDelay;

    @Value("${loader.period}")
    private int period;

    public ConversionLoader () {
    }

    @PostConstruct
    private void afterPropertiesSet () {
        if ( disabled != 0 ) {
            LOGGER.error("Conversion Loaders are disabled.");
            return;
        }

        LOGGER.info("Conversion Loaders are enabled.");
        for (GoalType goalType : GoalType.values()) {
            scheduler.scheduleAtFixedRate(new ConversionLoaderTask(goalType), initialDelay, period, TimeUnit.SECONDS);
        }
    }

    @PreDestroy
    private void shutdown () {
        this.scheduler.shutdownNow();
    }

}
