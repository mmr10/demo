package com.example.web;

import java.io.Serializable;

/**
 * DTO for transfering error message with a list of field errors.
 */
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String description;


    public ErrorDTO(String message) {
        this(message, null);
    }

    public ErrorDTO(String message, String description) {
        this.message = message;
        this.description = description;
    }


    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

}
