package com.example.customer.ujconnect;

import android.os.Parcel;
import android.os.Parcelable;

public class WorkShopDetails implements Parcelable {
    private String title;
    private String image;
    private String description;
    private String instructor;
    private String duration;
    private String date;
    private String time;
    private String location;
    private String dean;
    private String department;
    private String firebase_id;

    public WorkShopDetails() {
    }

    public WorkShopDetails(String title, String image, String description, String instructor, String duration, String date, String time, String location, String dean, String department, String firebase_id) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.instructor = instructor;
        this.duration = duration;
        this.date = date;
        this.time = time;
        this.location = location;
        this.dean = dean;
        this.department = department;
        this.firebase_id = firebase_id;
    }

    protected WorkShopDetails(Parcel in) {
        title = in.readString();
        image = in.readString();
        description = in.readString();
        instructor = in.readString();
        duration = in.readString();
        date = in.readString();
        time = in.readString();
        location = in.readString();
        dean = in.readString();
        department = in.readString();
        firebase_id = in.readString();
    }

    public static final Creator<WorkShopDetails> CREATOR = new Creator<WorkShopDetails>() {
        @Override
        public WorkShopDetails createFromParcel(Parcel in) {
            return new WorkShopDetails(in);
        }

        @Override
        public WorkShopDetails[] newArray(int size) {
            return new WorkShopDetails[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDean() {
        return dean;
    }

    public void setDean(String dean) {
        this.dean = dean;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(instructor);
        dest.writeString(duration);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(location);
        dest.writeString(dean);
        dest.writeString(department);
        dest.writeString(firebase_id);
    }
}
