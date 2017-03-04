package com.shiru.syntaxdb.api.request;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.octo.android.robospice.request.SpiceRequest;
import com.shiru.syntaxdb.api.RequestUtility;
import com.shiru.syntaxdb.api.SDBApi;
import com.shiru.syntaxdb.api.response.bean.CategoriesRsp;
import com.shiru.syntaxdb.bean.Category;
import com.shiru.syntaxdb.bean.Language;
import com.squareup.okhttp.Response;

import java.util.Arrays;

/**
 * Created by shiru on 1/23/2017.
 */
public class GetCategoriesRequest extends SpiceRequest<CategoriesRsp> {

    public static final String TAG = "SDB.GetCateRequest";
    private Language language;

    public GetCategoriesRequest(Language language) {
        super(CategoriesRsp.class);
        this.language = language;
    }

    @Override
    public CategoriesRsp loadDataFromNetwork() throws Exception {

        String url = String.format(SDBApi.GET_LANG_CATEGORIES_URL, language.getLink());

        Response response = RequestUtility.sendApiRequest(url, RequestUtility.HTTPMETHOD.GET, null);

        if (!response.isSuccessful()) {
            Log.d(TAG, "LOADDATAFROMNETWORK" + response.body().string());
        }

        String jsonRsp = response.body().string();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Category[] categories = gson.fromJson(jsonRsp, Category[].class);

        CategoriesRsp categoriesRsp = new CategoriesRsp();
        categoriesRsp.setCategoryList(Arrays.asList(categories));
        Log.d(TAG, "LOADFROMNETWORK" + categoriesRsp);

        return categoriesRsp;
    }
}
