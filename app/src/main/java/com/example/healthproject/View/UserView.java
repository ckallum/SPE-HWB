package com.example.healthproject.View;

/**
 * Class exposing authenticated user details to the UI.
 */
public class UserView {
    private String string;
    //... other data fields that may be accessible to the UI

    public UserView(String displayName) {
        this.string = displayName;
    }

    public String getString() {
        return string;
    }
}
