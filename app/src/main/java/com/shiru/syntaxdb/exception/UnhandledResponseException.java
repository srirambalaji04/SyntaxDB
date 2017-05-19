package com.shiru.syntaxdb.exception;


import okhttp3.Response;

/**
 * Created by bsriram on 26/04/16 12:24 PM in SyntaxDB.
 */
public class UnhandledResponseException extends Exception {

    private String message;
    private Response response;

    public UnhandledResponseException(String message, Response response) {
        super(message);
        this.message = message;
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
