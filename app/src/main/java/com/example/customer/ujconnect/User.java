package com.example.customer.ujconnect;




public class User {

    private String name, email, phoneNumber, UserType,RegistrationDate;


    public User() {

    }

    public User(String name, String email, String phoneNumber, String UserType, String RegistrationDate) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.UserType = UserType;
        this.RegistrationDate = RegistrationDate;

    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserType() {
        return UserType;
    }

    public String getRegistrationDate() {
        return RegistrationDate;
    }

}

