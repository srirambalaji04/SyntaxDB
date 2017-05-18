package com.shiru.syntaxdb.api.request;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.octo.android.robospice.request.SpiceRequest;
import com.shiru.syntaxdb.api.RequestUtility;
import com.shiru.syntaxdb.api.SDBApi;
import com.shiru.syntaxdb.api.response.bean.ConceptsRsp;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.utils.ExceptionHandler;
import com.squareup.okhttp.Response;

import java.net.URLEncoder;
import java.util.Arrays;

/**
 * Created by shiru on 5/17/2017.
 */

public class GetAllSearchesRequest extends SpiceRequest<ConceptsRsp> {

    private static final String TAG = "GetAllSearchesRequest";
    private String queryString;

    public GetAllSearchesRequest(String queryString) {
        super(ConceptsRsp.class);
        this.queryString = queryString;
    }

    @Override
    public ConceptsRsp loadDataFromNetwork() throws Exception {
        String url = SDBApi.GET_ALL_SEARCHES_URL + "q=";

        url = url + URLEncoder.encode(queryString, "utf-8");
        Log.d(TAG, "loadDataFromNetwork: " + url);

        Response response = RequestUtility.sendApiRequest(url, RequestUtility.HTTPMETHOD.GET, null);
        String json = response.body().string();
        Log.d(TAG, "loadDataFromNetwork : " + json);

        if (!response.isSuccessful()) {
            ExceptionHandler.handleRequestException(response);
            return null;
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Concept[] concepts = gson.fromJson(json, Concept[].class);

        ConceptsRsp conceptsRsp = new ConceptsRsp();
        conceptsRsp.setConcepts(Arrays.asList(concepts));

        return conceptsRsp;
    }
}
