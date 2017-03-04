package com.shiru.syntaxdb.api;

/**
 * Created by shiru on 1/16/2017.
 */
public class SDBApi {

    public static final String SERVER_BASE_URL = "https://syntaxdb.com/api/v1/";

    public static final String GET_ALL_LANGS_URL = SERVER_BASE_URL + "languages/";

    public static final String GET_SINGLE_LANG_URL = GET_ALL_LANGS_URL + "%s" + "/";

    public static final String GET_LANG_CATEGORIES_URL = GET_SINGLE_LANG_URL + "categories/";

    public static final String GET_CATE_CONCEPTS_URL = GET_LANG_CATEGORIES_URL + "%s" + "/concepts";
}
