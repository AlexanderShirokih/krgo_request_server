package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;

/**
 * Wrapper for error message
 */
@Data
public class ErrorWrapper {

    /**
     * Error title
     */
    private final String error;

    /**
     * Error description
     */
    private final String message;

}
