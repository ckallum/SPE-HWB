package com.example.healthproject.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.healthproject.Model.dto.User;
import com.example.healthproject.Model.dto.UserUpdateModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Class that handles authentication with Firebase w/ forgot credentials and retrieves user information.
 */
public class FirebaseDataSource {
    private FirebaseAuth mAuth;
    private Boolean resBool;

    public FirebaseDataSource() {
        this.mAuth = FirebaseAuth.getInstance();
        this.resBool = false;
    }

    Result<User> login(String email, String password) {
        this.resBool = false;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Success", "signInWithEmail:success");
                            resBool = true;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Fail", "signInWithEmail:failure", task.getException());
                        }

                        // ...
                    }
                });
        if (resBool) {
            User fakeUser =
                    new User(
                            mAuth.getCurrentUser());
            return new Result.Success<>(fakeUser);
        } else {
            return new Result.Error(new IOException("Error logging in"));
        }


    }

    Result<UserUpdateModel> register(String email, String password) {
        resBool = false;
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "signInWithEmail:success");
                    resBool = true;
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Fail", "signInWithEmail:failure", task.getException());
                }

                // ...
            }
        });
        if (resBool){
            UserUpdateModel reg = new UserUpdateModel();
            return new Result.Success<>(reg);
        }else {
            return new Result.Error(new IOException("Error Registering"));
        }
    }

    Result<UserUpdateModel> forgot(String email) {

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener((Executor) this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "signInWithEmail:success");
                    resBool = true;
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Fail", "signInWithEmail:failure", task.getException());
                }

                // ...
            }
        });
        if(resBool){
            UserUpdateModel reg = new UserUpdateModel();
            return new Result.Success<>(reg);
        }else{
            return new Result.Error(new IOException("Error Sending Email"));
        }
    }

    void logout() {
        try{
            FirebaseAuth.getInstance().signOut();
        }catch (Exception e){
            Log.w("Logout Fail", "signInWithEmail:failure", e);
        }
    }
}
