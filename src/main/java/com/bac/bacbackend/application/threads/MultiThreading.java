package com.bac.bacbackend.application.threads;

import com.bac.bacbackend.domain.port.IChrome;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class MultiThreading {

    protected ExecutorService threadPool = null;
    private IChrome browser;

    public MultiThreading(IChrome browser) {
        this.browser = browser;
    }

    protected abstract int waitTimeSeconds();
    protected abstract int sleepTimeMilliseconds();
    protected abstract int threadNumber();

    protected void createThreadPool() {
        threadPool = Executors.newFixedThreadPool(threadNumber());
    }

    protected void sleep() {
        try {
            Thread.sleep(sleepTimeMilliseconds());
        } catch (InterruptedException e) {
            System.err.println("Sleep interrupted: " + e.getMessage());
        }
    }

    protected void shutdown() {
        if (threadPool != null) threadPool.shutdown();
    }

    protected String getName() {
        return Thread.currentThread().getName();
    }

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
