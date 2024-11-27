package com.ccsw.tutorial.exceptions;

public class TutorialException extends RuntimeException {
    public TutorialException(String message) {
        super(message);
    }

    public TutorialException(String message, Throwable cause) {
        super(message, cause);
    }
}
