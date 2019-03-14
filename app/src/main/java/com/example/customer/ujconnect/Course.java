package com.example.customer.ujconnect;

public class Course {

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
}
