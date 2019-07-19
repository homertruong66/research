package com.hoang.lsp.loader;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hoang.lsp.service.LinkStatsService;
import com.hoang.lsp.task.LastClickAtLoaderTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

@Component
public class LastClickAtLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(LastClickAtLoader.class);

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
        1,
        new ThreadFactoryBuilder().
            setPriority(Thread.MAX_PRIORITY).
            setNameFormat("LastClickLoaderPool-Thread-%d").
            build()
    );

    @Autowired
    private LinkStatsService linkStatsService;

    @Value("${loader.last-clicked-at.disabled}")
    private int disabled;

    @Value("${loader.initial.delay}")
    private int initialDelay;

    @Value("${loader.period}")
    private int period;

    public LastClickAtLoader () {
    }

    @PostConstruct
    private void afterPropertiesSet () {
        if ( disabled != 0 ) {
            LOGGER.error("Last-clicked-at Loader is disabled.");
            return;
        }

        LOGGER.info("Last-clicked-at Loader is enabled.");
        scheduler.scheduleAtFixedRate(new LastClickAtLoaderTask(this.linkStatsService), initialDelay, period, TimeUnit.SECONDS);
    }

    @PreDestroy
    private void shutdown () {
        this.scheduler.shutdownNow();
    }

}
