package com.example.healthproject.Model;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthproject.Model.dto.Event;
import com.example.healthproject.Model.dto.User;
import com.example.healthproject.Model.dto.UserUpdateModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Class that handles authentication with Firebase w/ forgot credentials and retrieves user information.
 */
public class FirebaseDataSource extends AppCompatActivity {
    private FirebaseAuth auth;


    private FirebaseFirestore db;
    CollectionReference ref;

    public FirebaseDataSource() {
        this.auth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();

    }

    void login(String email, String password) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SUCCESS", "signInWithEmail:success");

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FAIL", "signInWithEmail:failure", task.getException());

                    // ...
                }
                // ...
            }
        });
        ;

    }

    void register(final String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.w("Success", "createUserWithEmail:success");
                    // Sign in success, update UI with the signed-in user's information
                    addDocument();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FAIL", "createUserWithEmail:failure", task.getException());
                }
                // ...
            }
        });
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

    private void addDocument() {
        Log.println(Log.ASSERT, "CHECK", auth.getCurrentUser().getEmail());
        ref = db.collection("users");
        User user = new User(auth.getCurrentUser().getEmail(), false);
        ref.document(auth.getCurrentUser().getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("SUCCESS", "DocumentSnapshot successfully written!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FAILURE", "Error adding document", e);
                    }
                });
    }

    public void add_event(Event event) {
        ref = db.collection("events");
        ref.add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("Success", "Event Created");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("FAIL", "Error adding document", e);
            }
        });
    }

    public FirebaseUser getAuthUser() {
        return auth.getCurrentUser();
    }
//    private Result<UserUpdateModel> successResult(UserUpdateModel res){
//        return new Result.Success<>(res);
//    }
//
//    private Result<LoggedInUser> successResult(LoggedInUser user){
//        return new Result.Success<>(user);
//    }
}