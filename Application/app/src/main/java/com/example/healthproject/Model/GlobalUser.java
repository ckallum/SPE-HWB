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

    private void setLoggedInUser( String email, Boolean status ) {
        this.user = new User( email, status );
    }

    public String getDisplayName() {
        return user.getDisplayName();
    }


    public boolean isAdmin() {
        return user != null && user.isAdmin();
    }

    public void login(final String username, String password) {
        // handle forgot
        dataSource.login(username, password);
        if (dataSource.getAuthUser() != null ) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference ref = db.collection("users");
            ref.document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        assert document != null;
                        if (document.exists()) {
                            Log.d("SUCCESS", "DocumentSnapshot data: " + document.getData());
                            setLoggedInUser( username, document.getBoolean("admin"));
                        } else {
                            Log.d("FAILURE", "No such document");
                        }
                    } else {
                        Log.d("FAILURE", "get failed with ", task.getException());
                    }
                }
            });
        }else {
            Log.d("FAILURE", "User Failed to Login");
        }
    }

    public Result<UserUpdateModel> register(String email ,String password){
        dataSource.register(email, password);
        if (dataSource.getAuthUser() == null) {
            Log.d("FAILURE", "User Failed to Register");
            return new Result.Error( new IOException("Error Registering Email"));
        }
        return new Result.Success<>(new UserUpdateModel());
    }

    public Result<UserUpdateModel> forgot(String email){

            Result<UserUpdateModel> result = dataSource.forgot(email);
            if (result instanceof Result.Error) {
                Log.d("FAILURE", "Failed to Send Email");
            }
            return result;
    }

}
