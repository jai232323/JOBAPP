package com.example.fastleader.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Jobfiedlvalue implements Parcelable {

    private String Jfid;
    private String Tid;
    private String color;
    private String size;

    public Jobfiedlvalue() {
    }

    public Jobfiedlvalue(String jfid, String tid, String color, String size) {
        Jfid = jfid;
        Tid = tid;
        this.color = color;
        this.size = size;
    }

    protected Jobfiedlvalue(Parcel in) {
        Jfid = in.readString();
        Tid = in.readString();
        color = in.readString();
        size = in.readString();
    }

    public static final Creator<Jobfiedlvalue> CREATOR = new Creator<Jobfiedlvalue>() {
        @Override
        public Jobfiedlvalue createFromParcel(Parcel in) {
            return new Jobfiedlvalue(in);
        }

        @Override
        public Jobfiedlvalue[] newArray(int size) {
            return new Jobfiedlvalue[size];
        }
    };

    public String getJfid() {
        return Jfid;
    }

    public void setJfid(String jfid) {
        Jfid = jfid;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Jfid);
        parcel.writeString(Tid);
        parcel.writeString(color);
        parcel.writeString(size);
    }
}
