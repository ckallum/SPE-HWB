package com.example.healthproject.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.healthproject.Model.dto.User;
import com.example.healthproject.Model.dto.UserUpdateModel;
import com.example.healthproject.Utils.Callback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

/**
 * Class that handles authentication with Firebase w/ forgot credentials and retrieves user information.
 */
public class FirebaseDataSource {
    private FirebaseAuth auth;

    public FirebaseDataSource() {
        this.auth = FirebaseAuth.getInstance();
    }

    Result<User> login(String email, String password) {

        try {
            auth.signInWithEmailAndPassword(email, password);
           User fakeUser =
                    new User(
                            auth.getCurrentUser());
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    Result<UserUpdateModel> register(String email, String password) {

        try {
            auth.createUserWithEmailAndPassword(email, password);
            UserUpdateModel reg = new UserUpdateModel();
            return new Result.Success<>(reg);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error Registering", e));
        }
    }

    Result<UserUpdateModel> forgot(String email) {
        try {
            auth.sendPasswordResetEmail(email);
            UserUpdateModel reg = new UserUpdateModel();
            return new Result.Success<>(reg);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error Sending Email", e));
        }
    }

    void logout() {
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