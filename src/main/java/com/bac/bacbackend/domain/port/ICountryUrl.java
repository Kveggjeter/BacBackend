package com.bac.bacbackend.domain.port;

public interface ICountryUrl {
    void save(String country, String url);
    boolean exist(String country);
}
