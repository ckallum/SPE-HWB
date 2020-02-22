package com.example.healthproject.Model;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.healthproject.Activity.LoginActivity;
import com.example.healthproject.Model.dto.LoggedInUser;
import com.example.healthproject.Model.dto.UserUpdateModel;


/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of forgot status and user credentials information.
 */
public class GlobalUser {

    private static volatile GlobalUser instance;

    private FirebaseDataSource dataSource;
    private Context context;
    private LoggedInUser user = null;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore

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

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public String getDisplayName() {
        return user.getDisplayName();
    }


    public boolean isAdmin() {
        return user != null && user.isAdmin();
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle forgot
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }else{
            Toast.makeText(context.getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public Result<UserUpdateModel> register(String email ,String password){
        Result<UserUpdateModel> result = dataSource.register(email, password);
        if (result instanceof Result.Error) {
            Toast.makeText(context.getApplicationContext(), "Register Error", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public Result<UserUpdateModel> forgot(String email){

            Result<UserUpdateModel> result = dataSource.forgot(email);
            if (result instanceof Result.Error) {
                Toast.makeText(context.getApplicationContext(), "Forgot Pass Error", Toast.LENGTH_SHORT).show();
            }
            return result;
    }

}
