package com.example.customer.ujconnect;

import android.os.Parcel;
import android.os.Parcelable;

public class Attendence implements Parcelable {

    private String name;
    private String firebase_id;

    public Attendence() {
    }

    public Attendence(String name, String firebase_id) {
        this.name = name;
        this.firebase_id = firebase_id;
    }

    protected Attendence(Parcel in) {
        name = in.readString();
        firebase_id = in.readString();
    }

    public static final Creator<Attendence> CREATOR = new Creator<Attendence>() {
        @Override
        public Attendence createFromParcel(Parcel in) {
            return new Attendence(in);
        }

        @Override
        public Attendence[] newArray(int size) {
            return new Attendence[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirebase_id() {
        return firebase_id;
    }

    public void setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(firebase_id);
    }
}
