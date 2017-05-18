package com.shiru.syntaxdb.exception;

/**
 * Created by bsriram on 18/5/17 12:37 PM in SyntaxDB.
 */

public class BadRequestException extends Exception {

    private String message;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
