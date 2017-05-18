package com.shiru.syntaxdb.exception;

/**
 * Created by bsriram on 4/05/16 12:22 PM in SyntaxDB.
 */
public class NotFoundException extends Exception {

    private String message;

    public NotFoundException(String message) {
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
