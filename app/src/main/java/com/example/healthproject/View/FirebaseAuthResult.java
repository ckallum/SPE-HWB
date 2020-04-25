package com.example.healthproject.View;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
public class FirebaseAuthResult {
    @Nullable
    private UserView success;
    @Nullable
    private Integer error;

    public FirebaseAuthResult(@Nullable Integer error) {
        this.error = error;
    }

    public FirebaseAuthResult(@Nullable UserView success) {
        this.success = success;
    }

    @Nullable
    public UserView getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
