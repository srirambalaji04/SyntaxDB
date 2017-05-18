package com.shiru.syntaxdb.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by bsriram on 18/5/17 11:46 AM in SyntaxDB.
 */

public class SDBApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
