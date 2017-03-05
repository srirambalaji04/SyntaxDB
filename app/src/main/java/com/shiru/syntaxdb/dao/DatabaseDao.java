package com.shiru.syntaxdb.dao;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.shiru.syntaxdb.BuildConfig;
import com.shiru.syntaxdb.bean.Concept;

import java.util.List;

/**
 * Created by shiru on 3/5/2017.
 */
public class DatabaseDao extends Application {

    private Context context;
    private SQLiteOpenHelper helper;

    public DatabaseDao(Context context) {
        this.context = context;
        helper = SyntaxDBassetHelper.newInstance(context);
    }

    public boolean insertConcept(Concept concept){
        ContentValues values = new ContentValues();
        values.put("id", concept.getId());
        values.put("name", concept.getName());
        values.put("categoryId", concept.getCategoryId());
        values.put("position", concept.getPosition());
        values.put("languageId", concept.getLanguageId());
        values.put("languageLink", concept.getLanguageLink());
        values.put("conceptSearch", concept.getConceptSearch());
        values.put("conceptLink", concept.getConceptLink());
        values.put("desc", concept.getDesc());
        values.put("syntax", concept.getSyntax());
        values.put("notes", concept.getNotes());
        values.put("example", concept.getExample());
        values.put("keywords", concept.getKeywords());
        values.put("related", concept.getRelated());
        values.put("documentation", concept.getDocumentation());

        if (isDbWritable()){
            long result = helper.getWritableDatabase().insert(BuildConfig.TABLE_CONCEPTS, null, values);
            return result > -1;
        }else{
            return false;
        }
    }

    public void deleteConcept(){

    }

    public List<Concept> getConcepts(){
        return null;
    }

    private boolean isDbWritable(){
        return helper.getWritableDatabase().isOpen() && !helper.getWritableDatabase().isReadOnly();
    }

}
