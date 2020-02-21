package com.example.healthproject.View;

/**
 * Class exposing authenticated user details to the UI.
 */
public class UserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    public UserView(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
