package com.bac.bacbackend.domain.service.routine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class MultiThreading  {

    protected ExecutorService pool = null;

    public MultiThreading() {}

    protected abstract int waitTime();
    protected abstract int sleepTime();
    protected abstract int threadNumber();

    protected void threadPool() {
        pool = Executors.newFixedThreadPool(threadNumber());
    }

    protected void sleep() {
        try {
            Thread.sleep(sleepTime());
        } catch (InterruptedException e) {
            System.err.println("Sleep interrupted: " + e.getMessage());
        }
    }

    protected void shutdown() {
        if (pool != null) pool.shutdown();
    }

    protected void stop(ExecutorService es) {
        es.shutdown();
        try {
            if(!es.awaitTermination(waitTime(), TimeUnit.SECONDS))
                es.shutdownNow();
        } catch (InterruptedException e) {
            es.shutdownNow();
        }
    }
}
