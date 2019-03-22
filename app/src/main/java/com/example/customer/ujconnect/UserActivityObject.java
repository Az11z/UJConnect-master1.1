package com.example.customer.ujconnect;

import android.os.Parcel;
import android.os.Parcelable;

public class UserActivityObject implements Parcelable {

    private String image;
    private String title;
    private String date;


    public UserActivityObject() {
    }

    public UserActivityObject(String image, String title, String date) {
        this.image = image;
        this.title = title;
        this.date = date;
    }

    protected UserActivityObject(Parcel in) {
        image = in.readString();
        title = in.readString();
        date = in.readString();
    }

    public static final Creator<UserActivityObject> CREATOR = new Creator<UserActivityObject>() {
        @Override
        public UserActivityObject createFromParcel(Parcel in) {
            return new UserActivityObject(in);
        }

        @Override
        public UserActivityObject[] newArray(int size) {
            return new UserActivityObject[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(date);
    }
}
