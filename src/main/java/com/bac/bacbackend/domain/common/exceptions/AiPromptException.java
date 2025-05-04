package com.bac.bacbackend.domain.common.exceptions;

/**
 * Custom exception, makes it easier to follow along where the LLM does mistakes
 */
public class AiPromptException extends IllegalArgumentException {

    /**
     * Constructor that extends the message found in {@link IllegalArgumentException}
     * @param message gives information about what went wrong
     */
    public AiPromptException(String message) {
        super(message);
    }

}
