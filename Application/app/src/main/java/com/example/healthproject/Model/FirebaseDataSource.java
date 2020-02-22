package com.example.healthproject.Model;

import com.example.healthproject.Model.dto.LoggedInUser;
import com.example.healthproject.Model.dto.UserUpdateModel;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

/**
 * Class that handles authentication with Firebase w/ forgot credentials and retrieves user information.
 */
public class FirebaseDataSource {
    FirebaseAuth auth;

    public FirebaseDataSource() {
        this.auth = FirebaseAuth.getInstance();
    }

    public Result<LoggedInUser> login(String email, String password) {

        try {
            auth.signInWithEmailAndPassword(email, password);
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            auth.getCurrentUser());
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<UserUpdateModel> register(String email, String password) {

        try {
            auth.createUserWithEmailAndPassword(email, password);
            UserUpdateModel reg = new UserUpdateModel();
            return new Result.Success<>(reg);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error Registering", e));
        }
    }

    public Result<UserUpdateModel> forgot(String email) {
        try {
            auth.sendPasswordResetEmail(email);
            UserUpdateModel reg = new UserUpdateModel();
            return new Result.Success<>(reg);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error Sending Email", e));
        }
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

//    private Result<UserUpdateModel> successResult(UserUpdateModel res){
//        return new Result.Success<>(res);
//    }
//
//    private Result<LoggedInUser> successResult(LoggedInUser user){
//        return new Result.Success<>(user);
//    }
}
