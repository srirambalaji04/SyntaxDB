package com.shiru.syntaxdb.utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shiru on 2/19/2017.
 */
public class UiUtility {

/*
    public static AlertDialog getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_progress, null, false);
        builder.setView(view);
        builder.setCancelable(false);
        return builder.create();
    }
*/

    /**
     * Displays a snackBar
     *
     * @param rootView View
     * @param msg      String
     */
    public static void showSnackBar(View rootView, String msg) {
        Snackbar snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG);

        View snackView = snackbar.getView();
        TextView textView = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);

        textView.setTextColor(Color.WHITE); //Change in all activities
        textView.setTextSize(22);
        textView.setMaxLines(3);
        snackbar.show();
    }
}
