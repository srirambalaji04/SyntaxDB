package com.shiru.syntaxdb.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.shiru.syntaxdb.R;

/**
 * Created by shiru on 2/19/2017.
 */
public class UiUtility {

    public static AlertDialog getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_progress, null, false);
        builder.setView(view);
        builder.setCancelable(false);
        return builder.create();
    }
}
