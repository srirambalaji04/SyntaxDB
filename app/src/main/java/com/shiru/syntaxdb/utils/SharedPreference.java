package com.shiru.syntaxdb.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.shiru.syntaxdb.bean.Concept;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by dkarthick on 8/3/17 11:45 AM in SyntaxDB.
 */

public class SharedPreference {

    private static final String PREFS_NAME = "SyntaxDB";

    private static final String PROPERTY_RECENTS = "recents";

    private static final String TAG = "SharedPreference";

    public SharedPreference() {
        super();
    }

    public static void saveRecent(Concept concept) {
        Queue<Concept> recents = getRecents();
        if (recents == null)
            recents = new LinkedList<>();
        if (!recents.contains(concept)) {
            if (recents.size() == 10)
                recents.remove();
            recents.offer(concept);
            saveRecents(recents);
        }
    }

    public static Queue<Concept> getRecents() {
        SharedPreferences settings;
        List<Concept> recents;
        Queue<Concept> recentsQ;

        settings = SDBApp.getAppContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (settings.contains(PROPERTY_RECENTS)) {
            String jsonSearch = settings.getString(PROPERTY_RECENTS, null);
            Gson gson = new Gson();
            Concept[] radiosArr = gson.fromJson(jsonSearch,
                    Concept[].class);
            recents = Arrays.asList(radiosArr);
            recents = new ArrayList<>(recents);
            recentsQ = new LinkedList<>(recents);
            Log.d(TAG, "recents: " + recentsQ);
        } else {
            return new LinkedList<>();//null;
        }

        return recentsQ;
    }

    private static void saveRecents(Queue<Concept> recents) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = SDBApp.getAppContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        String jsonSearches = new Gson().toJson(recents);
        editor.putString(PROPERTY_RECENTS, jsonSearches);
        editor.apply();
    }

}
