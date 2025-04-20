package com.bac.bacbackend.domain.port;

public interface IFailedRepo {
    boolean exists(String id);
    void adder(String s);
}
