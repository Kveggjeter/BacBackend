package com.bac.bacbackend.domain.common.exceptions;

public class AiPromptException extends IllegalArgumentException {

    public AiPromptException(String message) {
        super(message);
    }

    public AiPromptException(Throwable cause) {
        super(cause);
    }

    public AiPromptException(String message, Throwable cause) {
        super(message, cause);
    }

    public AiPromptException() {}
}
