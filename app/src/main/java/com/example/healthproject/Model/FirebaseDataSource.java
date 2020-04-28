package com.example.healthproject.Model;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthproject.Model.dto.Event;
import com.example.healthproject.Model.dto.User;
import com.example.healthproject.Model.dto.UserEvent;
import com.example.healthproject.Model.dto.UserUpdateModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.IOException;
import java.util.Objects;

/**
 * Class that handles authentication with Firebase w/ forgot credentials and retrieves user information.
 */
public class FirebaseDataSource extends AppCompatActivity {
    private FirebaseAuth auth;


    private FirebaseFirestore db;
    private CollectionReference ref;

    public FirebaseDataSource() {
        this.auth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();

    }

    public void register(final String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.w("Success", "createUserWithEmail:success");
                // Sign in success, update UI with the signed-in user's information
                addNewUser();

            } else {
                // If sign in fails, display a message to the user.
                Log.w("FAIL", "createUserWithEmail:failure", task.getException());
            }
            // ...
        });
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

    private void addNewUser() {
        Log.println(Log.ASSERT, "CHECK", auth.getCurrentUser().getEmail());
        ref = db.collection("users");
        User user = new User(auth.getCurrentUser().getEmail(), false);
        ref.document(auth.getCurrentUser().getEmail()).set(user).addOnSuccessListener(aVoid -> {
            Log.d("SUCCESS", "DocumentSnapshot successfully written!");
            logout();
        })
                .addOnFailureListener(e -> Log.w("FAILURE", "Error adding document", e));
    }

    public void addEvent(final Event event) {
        ref = db.collection("events");
        ref.add(event).addOnSuccessListener(documentReference -> {
            Log.d("Success", "Event Created");
            ref.document(documentReference.getId()).update("id", documentReference.getId());
        }).addOnFailureListener(e -> Log.w("FAIL", "Error adding document", e));
    }

    public void deleteEvent(String id) {
        ref = db.collection("events");
        ref.document(id).delete().addOnSuccessListener(aVoid -> Log.d("Success", "Event Deleted")).addOnFailureListener(e -> Log.w("FAIL", "Error deleting document", e));
        db.collection("user_event_link").whereEqualTo("eventId", id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    db.collection("user_event_link").document(document.getId()).delete().addOnSuccessListener(aVoid -> Log.d("Success", "Event Deleted"));
                }
            } else {
                Log.d("Fail", "Error deleting event: ", task.getException());
            }
        });
    }

    public void changeEvent(Event event) {
        ref = db.collection("events");
        ref.document(event.getId()).set(event).addOnSuccessListener(aVoid -> Log.d("Success", "Event Updated")).addOnFailureListener(e -> Log.w("FAIL", "Error Updating Document", e));
    }

    public void updateUserDisplayName(String id, String name) {
        ref = db.collection("users");
        ref.document(id).update("displayName", name).addOnSuccessListener(aVoid -> Log.d("Success", "Name Updated")).addOnFailureListener(e -> Log.w("FAIL", "Error Updating Document", e));
    }

    public void updatePassword(String password) {
        auth.getCurrentUser().updatePassword(password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Success", "User password updated.");
            }
        });

    }

    public void eventSubscribe(String uId, String eId) {
        UserEvent userEvent = new UserEvent(uId, eId);
        ref = db.collection("user_event_link");
        ref.add(userEvent).addOnSuccessListener(documentReference -> Log.d("Success", "Event Created")).addOnFailureListener(e -> Log.w("FAIL", "Error adding document", e));
        ref = db.collection("events");
        ref.document("eId").get().addOnSuccessListener(documentSnapshot -> ref.document("eId").update("interested", Objects.requireNonNull(documentSnapshot.getLong("attendees")).intValue()+1));
    }



    public void eventUnsubscribe(String email, String eventId) {
        db.collection("user_event_link").whereEqualTo("eventId", eventId).whereEqualTo("userId", email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("Success", document.getId() + " => " + document.getData());
                    db.collection("user_event_link").document(document.getId()).delete().addOnSuccessListener(aVoid -> Log.d("Success", "Event Unsubscribed"));
                }
            } else {
                Log.d("Fail", "Error getting documents: ", task.getException());
            }
        });



    }

}