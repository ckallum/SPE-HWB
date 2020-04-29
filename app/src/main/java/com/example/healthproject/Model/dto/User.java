package com.example.healthproject.Model.dto;

/**
 * Data class that captures user information for logged in users retrieved from GlobalUser
 */
public class User {

    private String displayName;
    private boolean isAdmin;
    private String email;

    public User(String email,  Boolean isAdmin) {
        this.isAdmin = isAdmin;
        this.email = email;
    }

    public User(){

    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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
