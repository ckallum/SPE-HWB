package com.example.healthproject.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.healthproject.Activity.LoginActivity;
import com.example.healthproject.Model.dto.User;
import com.example.healthproject.Model.dto.UserUpdateModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of forgot status and user credentials information.
 */
public class GlobalUser {

    @SuppressLint("StaticFieldLeak")
    private static volatile GlobalUser instance;

    private FirebaseDataSource dataSource;
    private User user = null;


    // private constructor : singleton access
    private GlobalUser(FirebaseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static GlobalUser getInstance(FirebaseDataSource dataSource) {
        if (instance == null) {
            instance = new GlobalUser(dataSource);
        }
        return instance;
    }


    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    public void setLoggedInUser(String email, Boolean status) {
        this.user = new User(email, status);
    }

    public String getDisplayName() {
        return user.getDisplayName();
    }


    public boolean isAdmin() {
        return user != null && user.isAdmin();
    }

    public String get_Id(){
        return user.getEmail();
    }

    public void login(final String username, Boolean admin) {
        setLoggedInUser(username, admin);

    }

    public Result<UserUpdateModel> register(String email, String password) {
        dataSource.register(email, password);
//        if (dataSource.getAuthUser() == null) {
//            Log.d("FAILURE", "User Failed to Register");
//            return new Result.Error( new IOException("Error Registering Email"));
//        }
        return new Result.Success<>(new UserUpdateModel());
    }

    public Result<UserUpdateModel> forgot(String email) {

        Result<UserUpdateModel> result = dataSource.forgot(email);
        if (result instanceof Result.Error) {
            Log.d("FAILURE", "Failed to Send Email");
        }
        return result;
    }

    public void update_displayName(String name){
        user.setDisplayName( name );
        dataSource.update_user_displayName(user.getEmail(), name);
    }
}
