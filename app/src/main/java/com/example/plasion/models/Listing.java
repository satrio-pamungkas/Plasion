package com.example.plasion.models;

public class Listing {
    private String createdDate;
    private String listingId;
    private String userId;
    private String userFullName;
    private String userEmail;
    private String userPhone;
    private String userLocation;
    private String userBlood;

    public Listing() {

    }

    public Listing(String createdDate, String listingId, String userId, String userFullName,
                   String userEmail, String userPhone, String userLocation, String userBlood) {
        this.createdDate = createdDate;
        this.listingId = listingId;
        this.userId = userId;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userLocation = userLocation;
        this.userBlood = userBlood;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getListingId() {
        return listingId;
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
