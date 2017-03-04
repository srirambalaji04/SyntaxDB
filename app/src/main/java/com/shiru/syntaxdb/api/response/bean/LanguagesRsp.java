package com.shiru.syntaxdb.api.response.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.shiru.syntaxdb.bean.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiru on 1/16/2017.
 */
public class LanguagesRsp implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LanguagesRsp> CREATOR = new Parcelable.Creator<LanguagesRsp>() {
        @Override
        public LanguagesRsp createFromParcel(Parcel in) {
            return new LanguagesRsp(in);
        }

        @Override
        public LanguagesRsp[] newArray(int size) {
            return new LanguagesRsp[size];
        }
    };
    @Expose
    private List<Language> languages;

    public LanguagesRsp() {
    }

    protected LanguagesRsp(Parcel in) {
        if (in.readByte() == 0x01) {
            languages = new ArrayList<Language>();
            in.readList(languages, Language.class.getClassLoader());
        } else {
            languages = null;
        }
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        return "LanguagesRsp{" +
                "languages=" + languages +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (languages == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(languages);
        }
    }
}
