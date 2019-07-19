package com.hoang.lsp.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hoang.lsp.core.errorhandler.RejectedHandler;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

@Service
public class ThreadPoolService {

    private ExecutorService threadPool;

    @Autowired
    public ThreadPoolService (
        @Value("${pool.core.size}") int poolCoreSize,
        @Value("${pool.max.size}") int poolMaxSize,
        @Value("${pool.queue.size}") int poolQueueSize,
        @Value("${pool.idle.timeout}") int poolIdleTimeout) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("ProcessorPool-Thread-%d").build();
        // If the thread pool has not reached the core size, it creates new threads.
        // If the core size has been reached and there is no idle threads, it queues tasks.
        // If the core size has been reached, there is no idle threads, and the queue becomes full, it creates new threads (until it reaches the max size).
        // If the max size has been reached, there is no idle threads, and the queue becomes full, the rejection policy kicks in.
        threadPool = new ThreadPoolExecutor(
            poolCoreSize,                                    // core size
            poolMaxSize,                                    // max size
            poolIdleTimeout,                                // idle timeout
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(poolQueueSize),// queue with a size
            threadFactory,                                  // thread factory
            new RejectedHandler()                           // Handle message when Queue is full
        );
    }

    public void execute (Runnable task) {
        threadPool.execute(task);
    }

    public void shutdown () {
        this.threadPool.shutdown();
    }

}
