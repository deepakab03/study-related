package com.deepak.questions.gedcom.exception;

public class GenealogyParserException extends GenealogyDataExtractorException {

    private static final long serialVersionUID = 1L;

    public GenealogyParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenealogyParserException(String message) {
        super(message);
    }

}
