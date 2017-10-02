package com.shiru.syntaxdb.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by shiru on 10/1/2017.
 */

public class SDBUtil {

    public static final String DATA_MARKET = "market://details?id=com.shiru.syntaxdb";

    public static final String DATA_URL = "https://play.google.com/store/apps/details?id=com.shiru.syntaxdb";

    public static void Share(Activity context) {
        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, DATA_URL);
//        intent.setData(Uri.parse(DATA_URL));
        context.startActivity(Intent.createChooser(intent, "Share to Friends"));

    }
}
