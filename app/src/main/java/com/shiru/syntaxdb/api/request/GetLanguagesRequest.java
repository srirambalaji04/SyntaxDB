package com.shiru.syntaxdb.api.request;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.octo.android.robospice.request.SpiceRequest;
import com.shiru.syntaxdb.api.RequestUtility;
import com.shiru.syntaxdb.api.SDBApi;
import com.shiru.syntaxdb.api.response.bean.LanguagesRsp;
import com.shiru.syntaxdb.bean.Language;
import com.shiru.syntaxdb.utils.ExceptionHandler;

import java.util.Arrays;

import okhttp3.Response;

/**
 * Created by shiru on 1/16/2017.
 */
public class GetLanguagesRequest extends SpiceRequest<LanguagesRsp> {

    private static final String TAG = "SDB.GetLanguagesRequest";

    public GetLanguagesRequest() {
        super(LanguagesRsp.class);
    }

    @Override
    public LanguagesRsp loadDataFromNetwork() throws Exception {
        String url = SDBApi.GET_ALL_LANGS_URL;

        Response response = RequestUtility.sendApiRequest(url, RequestUtility.HTTPMETHOD.GET, null);

        if (!response.isSuccessful()) {
            ExceptionHandler.handleRequestException(response);
            Log.e(TAG, response.body().string());
        }

        String jsonRsp = response.body().string();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Language[] languages = gson.fromJson(jsonRsp, Language[].class);
        LanguagesRsp languagesRsp = new LanguagesRsp();
        languagesRsp.setLanguages(Arrays.asList(languages));
        Log.d(TAG, languagesRsp.toString());
        return languagesRsp;
    }
}
