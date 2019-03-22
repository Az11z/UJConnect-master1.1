package com.example.customer.ujconnect;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable {

    String title;
    String urlToImage;
    String department;
    String departmentImage;
    String date;

    public Course() {
    }

    public Course(String title, String urlToImage, String department, String departmentImage, String date) {
        this.title = title;
        this.urlToImage = urlToImage;
        this.department = department;
        this.departmentImage = departmentImage;
        this.date = date;
    }


    protected Course(Parcel in) {
        title = in.readString();
        urlToImage = in.readString();
        department = in.readString();
        departmentImage = in.readString();
        date = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentImage() {
        return departmentImage;
    }

    public void setDepartmentImage(String departmentImage) {
        this.departmentImage = departmentImage;
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
        dest.writeString(title);
        dest.writeString(urlToImage);
        dest.writeString(department);
        dest.writeString(departmentImage);
        dest.writeString(date);
    }
}
