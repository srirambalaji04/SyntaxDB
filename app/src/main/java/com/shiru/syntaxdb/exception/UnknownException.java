package com.shiru.syntaxdb.exception;

/**
 * Created by bsriram on 26/04/16 12:24 PM in SyntaxDB.
 */
public class UnknownException extends Exception {

    private String message;

    public UnknownException(String message) {
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
