package com.deepak.studyrelated.ms.exception;

/**
 * @author Deepak Abraham
 */
public class ProcessException extends Exception {

    private static final long serialVersionUID = 1L;

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }

}
