package com.bac.bacbackend.domain.port;

public interface IOpenAi {
    String prompt(String command, String message);
}
