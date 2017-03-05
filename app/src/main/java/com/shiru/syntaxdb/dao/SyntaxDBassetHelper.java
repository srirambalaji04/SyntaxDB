package com.shiru.syntaxdb.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shiru.syntaxdb.BuildConfig;

/**
 * Created by shiru on 3/4/2017.
 */
public class SyntaxDBassetHelper extends SQLiteOpenHelper {

    private static final String TAG = "SDB.SyntaxDBassetHelper";

    public static SyntaxDBassetHelper newInstance(Context context){
        return new SyntaxDBassetHelper(context, BuildConfig.DATABASE, null, BuildConfig.DB_VERSION);
    }

    private SyntaxDBassetHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.rawQuery(createTableConcepts(), null);
    }

    private String createTableConcepts(){
        String CREATE_CONCEPTS = "CREATE TABLE " + BuildConfig.TABLE_CONCEPTS + " ("
        + "id TEXT, "
        + "name	TEXT, "
        + "categoryId TEXT, "
        + "position	INTEGER, "
        + "languageId TEXT, "
        + "languageLink TEXT, "
        + "conceptSearch TEXT, "
        + "conceptLink TEXT, "
        + "desc	TEXT, "
        + "syntax TEXT, "
        + "notes TEXT, "
        + "example TEXT, "
        + "keywords	INTEGER, "
        + "related TEXT, "
        + "documentation TEXT, "
        + "PRIMARY KEY(id, name, categoryId, languageId, languageLink));";
        return CREATE_CONCEPTS;
    }

    private String checkTables(){
        return "PRAGMA table_info([Concepts])";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
