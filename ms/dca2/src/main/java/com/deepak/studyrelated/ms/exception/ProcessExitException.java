package com.deepak.studyrelated.ms.exception;

/**
 * @author Deepak Abraham
 * 
 */
public class ProcessExitException extends ProcessException {

    private static final long serialVersionUID = 1L;

    public ProcessExitException(String message) {
        super(message);
    }

    public ProcessExitException(String message, Throwable cause) {
        super(message, cause);
    }

}
