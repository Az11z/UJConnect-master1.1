package com.example.customer.ujconnect;

import android.os.Parcel;
import android.os.Parcelable;

public class DepartmentCardView implements Parcelable {
    private String image;
    private String department_name;
    private String admin_name;
    private String admin_id;
    private String phone_number;
    private String email;
    private String firebase_id;

    public DepartmentCardView() {
    }

    public DepartmentCardView(String image, String department_name, String admin_name, String admin_id, String phone_number, String email, String firebase_id) {
        this.image = image;
        this.department_name = department_name;
        this.admin_name = admin_name;
        this.admin_id = admin_id;
        this.phone_number = phone_number;
        this.email = email;
        this.firebase_id = firebase_id;
    }

    protected DepartmentCardView(Parcel in) {
        image = in.readString();
        department_name = in.readString();
        admin_name = in.readString();
        admin_id = in.readString();
        phone_number = in.readString();
        email = in.readString();
        firebase_id = in.readString();
    }

    public static final Creator<DepartmentCardView> CREATOR = new Creator<DepartmentCardView>() {
        @Override
        public DepartmentCardView createFromParcel(Parcel in) {
            return new DepartmentCardView(in);
        }

        @Override
        public DepartmentCardView[] newArray(int size) {
            return new DepartmentCardView[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        dest.writeString(image);
        dest.writeString(department_name);
        dest.writeString(admin_name);
        dest.writeString(admin_id);
        dest.writeString(phone_number);
        dest.writeString(email);
        dest.writeString(firebase_id);
    }
}
