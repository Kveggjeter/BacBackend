package com.bac.bacbackend.application.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class MultiThreading {

    protected ExecutorService threadPool = null;

    public MultiThreading() {}

    /**
     * For setting a time-limit (seconds) on a thread
     * @return time for a loose thread to be shutdown
     */
    protected abstract int waitTimeSeconds();

    /**
     * For setting a sleep time (milliseconds) before a new iteration (or wherever)
     * @return a sleep time
     */
    protected abstract int sleepTimeMilliseconds();

    /**
     * For setting preferred number of threads. Be cautious with the number, as some recursive
     * methods quickly can take up a lot of hardware.
     *
     * @return number of threads
     */
    protected abstract int threadNumber();

    /**
     * For setting up a pool of threads. The Executors and ExecutorsService library
     * helps the threads to work in parallel with each of their own task, so no one does the
     * same thing.
     */
    protected void createThreadPool() {
        threadPool = Executors.newFixedThreadPool(threadNumber());
    }

    /**
     * Setting a sleep point somewhere in the code.
     */
    protected void sleep() {
        try {
            Thread.sleep(sleepTimeMilliseconds());
        } catch (InterruptedException e) {
            System.err.println("Sleep interrupted: " + e.getMessage());
        }
    }

    /**
     * Simple shutdown with a null checker. Only use in combination with stop to ensure
     * actual shutdowns. Some throws tend to skip shutdowns if not placed correctly,
     * so I've included this method for good measure. Sprinkling this in-between
     * throws does not hurt
     */
    protected void shutdown() {
        if (threadPool != null) threadPool.shutdown();
    }

    protected String getName() {
        return Thread.currentThread().getName();
    }

    /**
     * Method for stopping the thread(pool) after it is no longer needed.
     *
     * @param executorService representing the thread pool
     */
    protected void stop(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if(!executorService.awaitTermination(waitTimeSeconds(), TimeUnit.SECONDS))
                executorService.shutdownNow();
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
