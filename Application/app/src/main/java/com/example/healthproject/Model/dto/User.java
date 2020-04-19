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
    private String email;

    public User(FirebaseUser user) {
        this.userId = user.getUid();
        this.displayName = user.getDisplayName();
        this.isAdmin = false;
        this.email = user.getEmail();
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
