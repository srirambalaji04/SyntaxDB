package com.shiru.syntaxdb.api;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by shiru on 1/16/2017.
 */
public class RequestUtility {

    public static Response sendApiRequest(String url, HTTPMETHOD method, RequestBody body) throws IOException {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setConnectTimeout(30, TimeUnit.SECONDS);

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
