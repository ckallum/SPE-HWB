package com.example.healthproject.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.example.healthproject.Activity.LoginActivity;
import com.example.healthproject.Model.dto.User;
import com.example.healthproject.Model.dto.UserUpdateModel;


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of forgot status and user credentials information.
 */
public class GlobalUser {

    @SuppressLint("StaticFieldLeak")
    private static volatile GlobalUser instance;

    private FirebaseDataSource dataSource;
    private Context context;
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
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    private void setLoggedInUser(User user) {
        this.user = user;
    }

    public String getDisplayName() {
        return user.getDisplayName();
    }


    public boolean isAdmin() {
        return user != null && user.isAdmin();
    }

    public Result<User> login(String username, String password) {
        // handle forgot
        Result<User> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<User>) result).getData());
        }else{
//            Toast.makeText(context.getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public Result<UserUpdateModel> register(String email ,String password){
        Result<UserUpdateModel> result = dataSource.register(email, password);
        if (result instanceof Result.Error) {
//            Toast.makeText(context.getApplicationContext(), "Register Error", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public Result<UserUpdateModel> forgot(String email){

            Result<UserUpdateModel> result = dataSource.forgot(email);
            if (result instanceof Result.Error) {
//                Toast.makeText(context.getApplicationContext(), "Forgot Pass Error", Toast.LENGTH_SHORT).show();
            }
            return result;
    }

}
