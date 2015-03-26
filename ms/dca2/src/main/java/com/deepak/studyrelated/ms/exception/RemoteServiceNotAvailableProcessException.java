/**
 * RemoteServiceNotAvailableProcessException.java created on 2015
 * License as per GNU GNU GENERAL PUBLIC LICENSE Version 2
 */
package com.deepak.studyrelated.ms.exception;

/**
 * @author Deepak Abraham
 */
public class RemoteServiceNotAvailableProcessException extends ProcessException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public RemoteServiceNotAvailableProcessException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public RemoteServiceNotAvailableProcessException(String message, Throwable cause) {
        super(message, cause);
    }

}
