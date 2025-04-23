package com.bac.bacbackend.domain.port;

public interface IFailedRepo {
    boolean exists(String id);
    void addToFail(String s);
}
