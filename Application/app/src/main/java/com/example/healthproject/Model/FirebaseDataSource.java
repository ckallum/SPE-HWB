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

/**
 * Class that handles authentication with Firebase w/ forgot credentials and retrieves user information.
 */
public class FirebaseDataSource {
    private FirebaseAuth mAuth;
    private boolean result;

    public FirebaseDataSource() {
        this.mAuth = FirebaseAuth.getInstance();
        this.result = false;
    }

    Result<User> login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Success", "signInWithEmail:success");
                            setResult(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Fail", "signInWithEmail:failure", task.getException());
                            setResult(true);
                        }

                        // ...
                    }
                });
        if (this.result) {
            return new Result.Success<>(new User(mAuth.getCurrentUser()));
        } else {
            return new Result.Error(new IOException("Error logging in"));
        }


    }

    Result<UserUpdateModel> register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "registerEmail:success");
                    setResult(true);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Fail", "registerEmail:failure", task.getException());
                    setResult(false);
                }

                // ...
            }
        }));
        if (this.result) {
            UserUpdateModel reg = new UserUpdateModel();
            return new Result.Success<>(reg);
        } else {
            return new Result.Error(new IOException("Error Registering"));
        }
    }

    Result<UserUpdateModel> forgot(String email) {

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "forgotEmail:success");
                    setResult(true);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Fail", "forgotEmail:failure", task.getException());
                    setResult(false);

                }

                // ...
            }
        });
        if (this.result) {
            UserUpdateModel reg = new UserUpdateModel();
            return new Result.Success<>(reg);
        } else {
            return new Result.Error(new IOException("Error Sending Email"));
        }
    }

    void logout() {
        try {
            FirebaseAuth.getInstance().signOut();
        } catch (Exception e) {
            Log.w("Logout Fail", "logout:failure", e);
        }
    }
    private void setResult(boolean result){
        this.result = result;
    }
}
