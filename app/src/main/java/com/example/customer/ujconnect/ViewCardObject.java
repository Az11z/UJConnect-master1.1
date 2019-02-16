package com.example.customer.ujconnect;

public class ViewCardObject {

        private String name;
        private String title;
        private String time;
        private String comments;
        private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ViewCardObject(String name, String title, String time, String comments, int id) {

        this.name = name;
        this.title = title;
        this.time = time;
        this.comments = comments;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ViewCardObject() {
    }

    public void setName(String name) {
        this.name = name;
    }


}
