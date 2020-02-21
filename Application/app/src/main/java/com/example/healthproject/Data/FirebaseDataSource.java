package com.example.healthproject.Data;

import com.example.healthproject.Data.model.LoggedInUser;
import com.example.healthproject.Data.model.UserUpdateModel;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

/**
 * Class that handles authentication with Firebase w/ forgot credentials and retrieves user information.
 */
public class FirebaseDataSource {

    public Result<LoggedInUser> login(String email, String password) {

        try {
            FirebaseAuth instance = FirebaseAuth.getInstance();
            instance.signInWithEmailAndPassword(email, password);
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            instance.getCurrentUser());
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<UserUpdateModel> register(String email, String password) {

        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email, password);
            UserUpdateModel reg = new UserUpdateModel();
            return new Result.Success<>(reg);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error Registering", e));
        }
    }

    public Result<UserUpdateModel> forgot(String email) {
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(email);
            UserUpdateModel reg = new UserUpdateModel();
            return new Result.Success<>(reg);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error Registering", e));
        }
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }
}
