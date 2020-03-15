package com.example.healthproject.Model.dto;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

/**
 * Data class that captures user information for logged in users retrieved from GlobalUser
 */
public class User {

    private String userId;
    private String displayName;
    private boolean isAdmin;
    private Uri profilePicture;
    private String email;
    private String phoneNumber;

    public User(FirebaseUser user) {
        this.userId = user.getUid();
        this.displayName = user.getDisplayName();
        this.isAdmin = false;
        this.profilePicture = user.getPhotoUrl();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Uri getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Uri profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
