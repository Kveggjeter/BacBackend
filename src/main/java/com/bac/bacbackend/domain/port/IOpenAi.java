package com.bac.bacbackend.domain.port;

/**
 * Interface for the used LLM
 */
public interface IOpenAi {
    String prompt(String command, String message);
}
