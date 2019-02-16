package com.example.customer.ujconnect;

public class DepartmentCardView {

    private int dep_icon;
    private String dep_title;

    public DepartmentCardView(int dep_icon, String dep_title) {
        this.dep_icon = dep_icon;
        this.dep_title = dep_title;
    }

    public DepartmentCardView() { }

    public int getDep_icon() {
        return dep_icon;
    }

    public void setDep_icon(int dep_icon) {
        this.dep_icon = dep_icon;
    }

    public String getDep_title() {
        return dep_title;
    }

    public void setDep_title(String dep_title) {
        this.dep_title = dep_title;
    }
}
