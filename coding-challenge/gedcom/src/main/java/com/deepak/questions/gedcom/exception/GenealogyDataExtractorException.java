package com.deepak.questions.gedcom.exception;

public class GenealogyDataExtractorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GenealogyDataExtractorException(String message) {
        super(message);
    }

    public GenealogyDataExtractorException(String message, Throwable cause) {
        super(message, cause);
    }

}
