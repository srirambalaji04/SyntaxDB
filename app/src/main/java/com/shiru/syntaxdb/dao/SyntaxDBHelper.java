package com.shiru.syntaxdb.dao;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by shiru on 8/26/2017.
 */

public class SyntaxDBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "syntaxdb.sqlite";
    private static final int DATABASE_VERSION = 1;


    public SyntaxDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
