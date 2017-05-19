package com.shiru.syntaxdb.api.request;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.octo.android.robospice.request.SpiceRequest;
import com.shiru.syntaxdb.api.RequestUtility;
import com.shiru.syntaxdb.api.SDBApi;
import com.shiru.syntaxdb.api.response.bean.ConceptsRsp;
import com.shiru.syntaxdb.bean.Category;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.utils.ExceptionHandler;

import java.util.Arrays;

import okhttp3.Response;

/**
 * Created by shiru on 2/18/2017.
 */
public class GetConceptsRequest extends SpiceRequest<ConceptsRsp> {

    private static final String TAG = "SDB.GetConceptsRequest";
    private Category category;

    public GetConceptsRequest(Category category) {
        super(ConceptsRsp.class);
        this.category = category;
        Log.d(TAG, "category : " + category);
    }

    @Override
    public ConceptsRsp loadDataFromNetwork() throws Exception {

        String url = String.format(SDBApi.GET_CATE_CONCEPTS_URL, category.getLanguagelink(), category.getId());
        Log.d(TAG, "url : " + url);

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
