package com.example.plasion.models;

public class User {
    private String userId;
    private String userFullName;
    private String userEmail;
    private String userPhone;
    private String userLocation;
    private String userBlood;

    public User() {

    }

    public User(String userId, String userFullName, String userEmail, String userPhone,
                String userLocation, String userBlood) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userLocation = userLocation;
        this.userBlood = userBlood;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public String getUserBlood() {
        return userBlood;
    }
}
