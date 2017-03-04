package com.shiru.syntaxdb.api.response.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.shiru.syntaxdb.bean.Concept;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiru on 2/18/2017.
 */
public class ConceptsRsp implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ConceptsRsp> CREATOR = new Parcelable.Creator<ConceptsRsp>() {
        @Override
        public ConceptsRsp createFromParcel(Parcel in) {
            return new ConceptsRsp(in);
        }

        @Override
        public ConceptsRsp[] newArray(int size) {
            return new ConceptsRsp[size];
        }
    };
    @Expose
    private List<Concept> concepts;

    public ConceptsRsp() {
    }

    protected ConceptsRsp(Parcel in) {
        if (in.readByte() == 0x01) {
            concepts = new ArrayList<Concept>();
            in.readList(concepts, Concept.class.getClassLoader());
        } else {
            concepts = null;
        }
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }

    @Override
    public String toString() {
        return "ConceptsRsp{" +
                "concepts=" + concepts +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (concepts == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(concepts);
        }
    }
}
