package com.example.healthproject.Model.dto;

import com.google.firebase.auth.FirebaseUser;

/**
 * Data class that captures user information for logged in users retrieved from GlobalUser
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private boolean isAdmin;

    public LoggedInUser(FirebaseUser user) {
        this.userId = user.getUid();
        this.displayName = user.getDisplayName();
        this.isAdmin = false;
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
}
