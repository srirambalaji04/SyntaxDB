package com.shiru.syntaxdb.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shiru on 1/16/2017.
 */
public class Category implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
    @Expose
    private int id;
    @Expose
    @SerializedName("category_name")
    private String name;
    @Expose
    @SerializedName("category_search")
    private String search;
    @Expose
    @SerializedName("language_id")
    private String languageId;
    @Expose
    @SerializedName("language_permalink")
    private String languagelink;
    @Expose
    private int position;

    public Category() {
    }

    protected Category(Parcel in) {
        id = in.readInt();
        name = in.readString();
        search = in.readString();
        languageId = in.readString();
        languagelink = in.readString();
        position = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getLanguagelink() {
        return languagelink;
    }

    public void setLanguagelink(String languagelink) {
        this.languagelink = languagelink;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", search='" + search + '\'' +
                ", languageId='" + languageId + '\'' +
                ", languagelink='" + languagelink + '\'' +
                ", position=" + position +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(search);
        dest.writeString(languageId);
        dest.writeString(languagelink);
        dest.writeInt(position);
    }
}