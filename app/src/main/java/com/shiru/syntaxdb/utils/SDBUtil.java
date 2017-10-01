package com.shiru.syntaxdb.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by shiru on 10/1/2017.
 */

public class SDBUtil {

    public static void Share(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setData(Uri.parse("market://details?id=com.shiru.syntaxdb"));
        context.startActivity(intent);

    }
}
