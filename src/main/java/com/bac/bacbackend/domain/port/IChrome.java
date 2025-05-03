package com.bac.bacbackend.domain.port;

/**
 * Interface used to interact with the browser instances across the application structure
 */
public interface IChrome {
    void start(String s);
    void stop();
}
