package com.shiru.syntaxdb.api;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by shiru on 1/16/2017.
 */
public class RequestUtility {

    public static Response sendApiRequest(String url, HTTPMETHOD method, RequestBody body) throws IOException {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = clientBuilder.build();

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        switch (method) {
            case GET:
                builder.method(method.name(), null);
                break;
            case POST:
                builder.method(method.name(), body);
                break;
            case PUT:
                builder.method(method.name(), body);
                break;
            case DELETE:
                builder.method(method.name(), body);
                break;
            default:
                builder.method(method.name(), null);
                break;
        }
        Request request = builder.build();
        return client.newCall(request).execute();
    }


    public enum HTTPMETHOD {
        GET, POST, PUT, DELETE;
    }
}
