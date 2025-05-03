package com.bac.bacbackend.domain.port;

/**
 * Interface for the repository handling all failed articles
 */
public interface IFailedRepo {
    boolean exists(String id);
    void addToFail(String s);
}
