package com.shiru.syntaxdb.utils;

import android.view.View;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.shiru.syntaxdb.exception.BadRequestException;
import com.shiru.syntaxdb.exception.NotFoundException;
import com.shiru.syntaxdb.exception.UnknownException;

import java.net.HttpURLConnection;

import okhttp3.Response;

/**
 * Utility class for handle server api exceptions.
 */
public class ExceptionHandler {
    /**
     * gets the response based on response code throws exceptions
     *
     * @param response OkHttp Response
     * @throws Exception
     */
    public static void handleRequestException(Response response) throws Exception {
        switch (response.code()) {
            case HttpURLConnection.HTTP_NOT_FOUND:
                throw new NotFoundException("Resource not found...");
            case HttpURLConnection.HTTP_BAD_REQUEST:
                throw new BadRequestException("Bad request...");
            default:
                throw new UnknownException("Unknown Err occured. please try later");
        }

    }

    /**
     * Gets the SpiceException,Activity and View for showing snack bar with exception
     *
     * @param e          SpiceException instance
     * @param rootLayout view instance
     */
    public static void handleListenerException(SpiceException e, View rootLayout) {
        String msg = null;
        if (e.getCause() != null) {
            msg = e.getCause().getMessage();
        } else {
            e.getMessage();
        }
        UiUtility.showSnackBar(rootLayout, msg);
    }
}

