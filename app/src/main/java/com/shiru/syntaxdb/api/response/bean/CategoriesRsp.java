package com.shiru.syntaxdb.api.response.bean;

import com.google.gson.annotations.Expose;
import com.shiru.syntaxdb.bean.Category;

import java.util.List;

/**
 * Created by shiru on 1/23/2017.
 */
public class CategoriesRsp {

    @Expose
    private List<Category> categoryList;

    public CategoriesRsp() {
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "CategoriesRsp{" +
                "categoryList=" + categoryList +
                '}';
    }
}
