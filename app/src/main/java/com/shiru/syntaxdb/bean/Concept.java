package com.shiru.syntaxdb.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shiru on 1/16/2017.
 */
public class Concept implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Concept> CREATOR = new Parcelable.Creator<Concept>() {
        @Override
        public Concept createFromParcel(Parcel in) {
            return new Concept(in);
        }

        @Override
        public Concept[] newArray(int size) {
            return new Concept[size];
        }
    };
    @Expose
    private String id;
    @Expose
    @SerializedName("concept_name")
    private String name;
    @Expose
    @SerializedName("category_id")
    private String categoryId;
    @Expose
    private int position;
    @Expose
    @SerializedName("language_id")
    private String languageId;
    @Expose
    @SerializedName("language_permalink")
    private String languageLink;
    @Expose
    @SerializedName("concept_search")
    private String conceptSearch;
    @Expose
    @SerializedName("concept_permalink")
    private String conceptLink;
    @Expose
    @SerializedName("description")
    private String desc;
    @Expose
    @SerializedName("syntax")
    private String syntax;
    @Expose
    @SerializedName("notes")
    private String notes;
    @Expose
    private String example;
    @Expose
    private String keywords;
    @Expose
    private String related;
    @Expose
    private String documentation;

    public Concept() {
    }

    protected Concept(Parcel in) {
        id = in.readString();
        name = in.readString();
        categoryId = in.readString();
        position = in.readInt();
        languageId = in.readString();
        languageLink = in.readString();
        conceptSearch = in.readString();
        conceptLink = in.readString();
        desc = in.readString();
        syntax = in.readString();
        notes = in.readString();
        example = in.readString();
        keywords = in.readString();
        related = in.readString();
        documentation = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getLanguageLink() {
        return languageLink;
    }

    public void setLanguageLink(String languageLink) {
        this.languageLink = languageLink;
    }

    public String getConceptSearch() {
        return conceptSearch;
    }

    public void setConceptSearch(String conceptSearch) {
        this.conceptSearch = conceptSearch;
    }

    public String getConceptLink() {
        return conceptLink;
    }

    public void setConceptLink(String conceptLink) {
        this.conceptLink = conceptLink;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(categoryId);
        dest.writeInt(position);
        dest.writeString(languageId);
        dest.writeString(languageLink);
        dest.writeString(conceptSearch);
        dest.writeString(conceptLink);
        dest.writeString(desc);
        dest.writeString(syntax);
        dest.writeString(notes);
        dest.writeString(example);
        dest.writeString(keywords);
        dest.writeString(related);
        dest.writeString(documentation);
    }
}