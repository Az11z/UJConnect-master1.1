package com.example.customer.ujconnect;

import android.os.Parcel;
import android.os.Parcelable;

public class Certificate implements Parcelable {
    private String date;
    private String dean;
    private String dep;
    private String hours;
    private String id;
    private String instructor;
    private String name;
    private String title;
    private String location;

    public Certificate() {
    }

    public Certificate(String date, String dean, String dep, String hours, String id, String instructor, String name, String title, String location) {
        this.date = date;
        this.dean = dean;
        this.dep = dep;
        this.hours = hours;
        this.id = id;
        this.instructor = instructor;
        this.name = name;
        this.title = title;
        this.location = location;
    }

    protected Certificate(Parcel in) {
        date = in.readString();
        dean = in.readString();
        dep = in.readString();
        hours = in.readString();
        id = in.readString();
        instructor = in.readString();
        name = in.readString();
        title = in.readString();
        location = in.readString();
    }

    public static final Creator<Certificate> CREATOR = new Creator<Certificate>() {
        @Override
        public Certificate createFromParcel(Parcel in) {
            return new Certificate(in);
        }

        @Override
        public Certificate[] newArray(int size) {
            return new Certificate[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDean() {
        return dean;
    }

    public void setDean(String dean) {
        this.dean = dean;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(dean);
        dest.writeString(dep);
        dest.writeString(hours);
        dest.writeString(id);
        dest.writeString(instructor);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(location);
    }
}
