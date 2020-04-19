package com.example.healthproject.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.healthproject.Model.dto.User;
import com.example.healthproject.Model.dto.UserUpdateModel;
import com.example.healthproject.Utils.Callback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

import static com.firebase.ui.auth.AuthUI.TAG;

/**
 * Class that handles authentication with Firebase w/ forgot credentials and retrieves user information.
 */
public class FirebaseDataSource {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    FirebaseUser authUser;
    CollectionReference ref;

    public FirebaseDataSource() {
        this.auth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();

    }

    Result<User> login(String email, String password) {

        try {
            auth.signInWithEmailAndPassword(email, password);
            authUser=auth.getCurrentUser();
            Log.d("CHECK", auth.getUid()+authUser.getEmail());
            ref = db.collection("users");
            ref.document(authUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        assert document != null;
                        if (document.exists()) {
                            Log.d("SUCCESS", "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d("FAILURE", "No such document");
                        }
                    } else {
                        Log.d("FAILURE", "get failed with ", task.getException());
                    }
                }
            });
//            DocumentSnapshot document = ref.document("users").get().getResult();
//            Log.d("SUCCESS", "DocumentSnapshot data: " + document.getData());
            return new Result.Success<>(new User( authUser.getEmail(), true));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    Result<UserUpdateModel> register(String email, String password) {
        try {
            auth.createUserWithEmailAndPassword(email, password);
            ref = db.collection("users");
            assert authUser != null;
            User user = new User(email, false);
            ref.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d("SUCCESS", "DocumentSnapshot written with ID: " + documentReference.getId());
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FAILURE", "Error adding document", e);
                        }
                    });
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