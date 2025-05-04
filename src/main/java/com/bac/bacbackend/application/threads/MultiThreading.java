package com.bac.bacbackend.application.threads;

import com.bac.bacbackend.domain.port.IChrome;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Abstract class for multi-threading. It is intended to be used as a base class
 * for future endeavours, but can also be extended for only using the threading
 * capabilities. Be aware that there are three mandatory abstract int methods.
 * They can all be set to return "0" if the methods are not in the wished use case.
 */
public abstract class MultiThreading {

    protected ExecutorService threadPool = null;
    private final IChrome browser;

    /**
     * Constructor for instancing the browser, that browser is meant to be inheritated by children classes
     * @param browser {@link IChrome}
     */
    protected MultiThreading(IChrome browser) {
        this.browser = browser;
    }

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

    /**
     * Setting a name for which thread-instance that are active, mostly for logging.
     * @return the name of the current thread (and pool)
     */
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
            if(!executorService.awaitTermination(waitTimeSeconds(), TimeUnit.SECONDS)) {
                browser.stop();
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            browser.stop();
        } finally {
            browser.stop();
        }
    }
}
