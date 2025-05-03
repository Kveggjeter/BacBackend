package com.bac.bacbackend.domain.common.exceptions;

/**
 * Custom exception class for better targeting when the regex fails
 */
public class RegexMatchResultException extends IllegalArgumentException {

    /**
     * Constructor that extends the message found in {@link IllegalArgumentException}
     * @param message gives information about what went wrong
     */
    public RegexMatchResultException(String message) {}

}
