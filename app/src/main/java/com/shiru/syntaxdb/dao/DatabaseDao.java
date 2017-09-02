package com.shiru.syntaxdb.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shiru.syntaxdb.bean.Category;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.bean.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiru on 3/5/2017.
 */
public class DatabaseDao {

    private static DatabaseDao INSTANCE;
    private SyntaxDBHelper helper;

    private DatabaseDao(Context context) {
        helper = new SyntaxDBHelper(context);
    }

    public static DatabaseDao getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseDao(context);
        }
        return INSTANCE;
    }

    public List<Language> getLanguages() {
        List<Language> languages = new ArrayList<>();
        String getAllLanguagesQuery = "SELECT * FROM language";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cr = db.rawQuery(getAllLanguagesQuery, null);
        if (cr != null && cr.getCount() > 0) {
            cr.moveToFirst();
            while (cr.moveToNext()) {
                Language language = new Language();
                language.setId(cr.getInt(cr.getColumnIndex("id")));
                language.setLink(cr.getString(cr.getColumnIndex("language_permalink")));
                language.setName(cr.getString(cr.getColumnIndex("language_name")));
                language.setVersion(cr.getString(cr.getColumnIndex("language_version")));
                language.setPosition(cr.getInt(cr.getColumnIndex("position")));

                languages.add(language);
            }
            cr.close();
        }
        return languages;
    }


    public List<Category> getCategoriesOfLanguage(Language language) {
        List<Category> categories = new ArrayList<>();
        String getAllCategoriesForLangSQL = "SELECT * FROM category WHERE language_id = " + language.getId();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cr = db.rawQuery(getAllCategoriesForLangSQL, null);
        if (cr != null && cr.getCount() > 0) {
            cr.moveToFirst();
            while (cr.moveToNext()) {
                Category category = new Category();
                category.setId(cr.getInt(cr.getColumnIndex("id")));
                category.setName(cr.getString(cr.getColumnIndex("category_name")));
                category.setPosition(cr.getInt(cr.getColumnIndex("position")));
                category.setLanguageId(cr.getInt(cr.getColumnIndex("language_id")) + "");
                category.setLanguagelink(cr.getString(cr.getColumnIndex("language_permalink")));
                category.setSearch(cr.getString(cr.getColumnIndex("category_search")));

                categories.add(category);
            }
            cr.close();
        }

        return categories;
    }

    public List<Concept> getConceptForCategories(Category category) {
        List<Concept> concepts = new ArrayList<>();
        String getAllCategoriesForLangSQL = "SELECT * FROM concept WHERE category_id = " + category.getId() +
                " AND language_id = " + category.getLanguageId();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cr = db.rawQuery(getAllCategoriesForLangSQL, null);
        if (cr != null && cr.getCount() > 0) {
            cr.moveToFirst();
            while (cr.moveToNext()) {
                Concept concept = new Concept();
                concept.setId(cr.getInt(cr.getColumnIndex("id")) + "");
                concept.setName(cr.getString(cr.getColumnIndex("concept_name")));
                concept.setPosition(cr.getInt(cr.getColumnIndex("position")));
                concept.setLanguageId(cr.getInt(cr.getColumnIndex("language_id")) + "");
                concept.setCategoryId(cr.getInt(cr.getColumnIndex("category_id")) + "");
                concept.setConceptLink(cr.getString(cr.getColumnIndex("concept_permalink")));
                concept.setConceptSearch(cr.getString(cr.getColumnIndex("concept_search")));
                concept.setDesc(cr.getString(cr.getColumnIndex("description")));
                concept.setDocumentation(cr.getString(cr.getColumnIndex("documentation")));
                concept.setExample(cr.getString(cr.getColumnIndex("example")));
                concept.setKeywords(cr.getString(cr.getColumnIndex("keywords")));
                concept.setLanguageLink(cr.getString(cr.getColumnIndex("language_permalink")));
                concept.setNotes(cr.getString(cr.getColumnIndex("notes")));
                concept.setRelated(cr.getString(cr.getColumnIndex("related")));
                concept.setSyntax(cr.getString(cr.getColumnIndex("syntax")));

                concepts.add(concept);
            }
            cr.close();
        }

        return concepts;
    }

    public List<Concept> getSearchConcepts(String searchQuery) {
        searchQuery = "%" + searchQuery + "%";
        List<Concept> concepts = new ArrayList<>();
        String getAllCategoriesForLangSQL = "select * from concept where concept_search OR concept_name OR concept_permalink OR description LIKE "
                + "'" + searchQuery + "'";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cr = db.rawQuery(getAllCategoriesForLangSQL, null);
        if (cr != null && cr.getCount() > 0) {
            cr.moveToFirst();
            while (cr.moveToNext()) {
                Concept concept = new Concept();
                concept.setId(cr.getInt(cr.getColumnIndex("id")) + "");
                concept.setName(cr.getString(cr.getColumnIndex("concept_name")));
                concept.setPosition(cr.getInt(cr.getColumnIndex("position")));
                concept.setLanguageId(cr.getInt(cr.getColumnIndex("language_id")) + "");
                concept.setCategoryId(cr.getInt(cr.getColumnIndex("category_id")) + "");
                concept.setConceptLink(cr.getString(cr.getColumnIndex("concept_permalink")));
                concept.setConceptSearch(cr.getString(cr.getColumnIndex("concept_search")));
                concept.setDesc(cr.getString(cr.getColumnIndex("description")));
                concept.setDocumentation(cr.getString(cr.getColumnIndex("documentation")));
                concept.setExample(cr.getString(cr.getColumnIndex("example")));
                concept.setKeywords(cr.getString(cr.getColumnIndex("keywords")));
                concept.setLanguageLink(cr.getString(cr.getColumnIndex("language_permalink")));
                concept.setNotes(cr.getString(cr.getColumnIndex("notes")));
                concept.setRelated(cr.getString(cr.getColumnIndex("related")));
                concept.setSyntax(cr.getString(cr.getColumnIndex("syntax")));

                concepts.add(concept);
            }
            cr.close();
        }

        return concepts;
    }


}
