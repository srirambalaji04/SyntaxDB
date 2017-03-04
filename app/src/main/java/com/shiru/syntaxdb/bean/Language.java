package com.shiru.syntaxdb.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shiru on 1/16/2017.
 */
public class Language implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Language> CREATOR = new Parcelable.Creator<Language>() {
        @Override
        public Language createFromParcel(Parcel in) {
            return new Language(in);
        }

        @Override
        public Language[] newArray(int size) {
            return new Language[size];
        }
    };
    @Expose
    private int id;
    @Expose
    @SerializedName("language_name")
    private String name;
    @Expose
    private int position;
    @Expose
    @SerializedName("language_version")
    private String version;
    @Expose
    @SerializedName("language_permalink")
    private String link;

    public Language() {
    }

    protected Language(Parcel in) {
        id = in.readInt();
        name = in.readString();
        position = in.readInt();
        version = in.readString();
        link = in.readString();
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", version='" + version + '\'' +
                ", link='" + link + '\'' +
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
        dest.writeInt(position);
        dest.writeString(version);
        dest.writeString(link);
    }
}