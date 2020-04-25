package com.example.healthproject.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthproject.Model.dto.Event;
import com.example.healthproject.R;
import com.example.healthproject.View.FirebaseAuthResult;
import com.example.healthproject.View.FormState;
import com.example.healthproject.View.UserView;
import com.example.healthproject.View.ViewModelController;
import com.example.healthproject.View.ViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UpdateEvent extends AppCompatActivity {
    private ViewModelController updateViewModel;
    private EditText eventName;
    private EditText eventSpaces;
    private EditText eventDate;
    private EditText eventStart;
    private EditText eventEnd;
    private EditText description;
    private Button updateButton;
    private Button deleteButton;
    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        setContentView(R.layout.fragment_event_update);
        updateViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ViewModelController.class);
        eventName = findViewById(R.id.event_name);
        eventSpaces= findViewById(R.id.event_spaces);//
        final Spinner eventVenue = findViewById(R.id.event_location);
        eventDate = findViewById(R.id.event_date);
        eventStart = findViewById(R.id.event_start);
        eventEnd = findViewById(R.id.event_end);
        description = findViewById(R.id.event_description);
        updateButton = findViewById(R.id.button_change);
        deleteButton = findViewById(R.id.button_delete);
        db = FirebaseFirestore.getInstance();
        CollectionReference ref = db.collection("events");
        DocumentReference docRef = db.collection("events").document(intent.getStringExtra("ID"));

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Event event = documentSnapshot.toObject(Event.class);
                eventName.setText(event.getName());
                eventSpaces.setText(String.valueOf(event.getAttendees()));
                eventDate.setText(event.getDate());
                eventStart.setText(event.getStart());
                eventEnd.setText(event.getEnd());
                description.setText(event.getDescription());
                eventVenue.setPrompt(event.getLocation());
            }
        });

        final List<String> venues = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, venues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventVenue.setAdapter(adapter);
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        String v = document.getString("name");
                        venues.add(v);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        updateViewModel.getFormState().observe(this, new Observer<FormState>() {
            @Override
            public void onChanged(FormState formState) {
                if (formState == null) {
                    return;
                }
                updateButton.setEnabled(formState.isDataValid());
                if (formState.getEventNameError() != null) {
                    eventName.setError(getString(formState.getEventNameError()));
                }else{
                    eventName.setError(null);
                }
                if (formState.getEventDateError() != null) {
                    eventDate.setError(getString(formState.getEventDateError()));
                }
                else{
                    eventDate.setError(null);
                }
                if (formState.getEventStartTimeError() != null) {
                    eventStart.setError(getString(formState.getEventStartTimeError()));
                }
                else{
                    eventStart.setError(null);
                }
                if (formState.getEventEndTimeError() != null) {
                    eventEnd.setError(getString(formState.getEventEndTimeError()));
                }
                else{
                    eventEnd.setError(null);
                }
                if (formState.getEventAttendeeError() != null){
                    eventSpaces.setError(getString(formState.getEventAttendeeError()));
                }
                else{
                    eventSpaces.setError(null);
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateViewModel.eventDataChanged(eventName.getText().toString(), eventStart.getText().toString(), eventEnd.getText().toString(), eventDate.getText().toString(), eventSpaces.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateViewModel.eventDataChanged(eventName.getText().toString(), eventStart.getText().toString(), eventEnd.getText().toString(), eventDate.getText().toString(), eventSpaces.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateViewModel.eventDataChanged(eventName.getText().toString(), eventStart.getText().toString(), eventEnd.getText().toString(), eventDate.getText().toString(), eventSpaces.getText().toString());
            }
        };
        eventName.addTextChangedListener(afterTextChangedListener);
        eventStart.addTextChangedListener(afterTextChangedListener);
        eventSpaces.addTextChangedListener(afterTextChangedListener);
        eventEnd.addTextChangedListener(afterTextChangedListener);
        eventDate.addTextChangedListener(afterTextChangedListener);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewModel.update_event(eventName.getText().toString(), eventStart.getText().toString(), eventEnd.getText().toString(), eventDate.getText().toString(), eventSpaces.getText().toString(), eventVenue.getSelectedItem().toString(), description.getText().toString(), intent.getStringExtra("ID"));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewModel.delete_event(intent.getStringExtra("ID"));
            }
        });
        updateViewModel.getAuthResult().observe(this, new Observer<FirebaseAuthResult>() {
            @Override
            public void onChanged(FirebaseAuthResult firebaseAuthResult) {
                if (firebaseAuthResult == null){
                    return;
                }
                if (firebaseAuthResult.getError() != null ){
                    showFailed(firebaseAuthResult.getError());
                }
                if (firebaseAuthResult.getSuccess()!=null){
                    showSuccess(firebaseAuthResult.getSuccess());
                }
            }
        });
    }
    private void showSuccess(UserView success) {
        Toast.makeText(this, success.getString(), Toast.LENGTH_SHORT).show();
    }

    private void showFailed(@StringRes Integer errorString) {
        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
    }

}
