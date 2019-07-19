package com.hoang.lsp.loader;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hoang.lsp.task.ClickLoaderTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

@Component
public class ClickLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClickLoader.class);

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(
        new ThreadFactoryBuilder().
            setPriority(Thread.MAX_PRIORITY).
            setNameFormat("ClickLoaderPool-Thread-%d").
            build()
    );

    @Value("${loader.click.disabled}")
    private int disabled;

    @Value("${loader.initial.delay}")
    private int initialDelay;

    @Value("${loader.period}")
    private int period;

    public ClickLoader () {
    }

    @PostConstruct
    private void afterPropertiesSet () {
        if ( disabled != 0 ) {
            LOGGER.error("Click Loader is disabled.");
            return;
        }

        LOGGER.info("Click Loader is enabled.");
        scheduler.scheduleAtFixedRate(new ClickLoaderTask(), initialDelay, period, TimeUnit.SECONDS);
    }

}
