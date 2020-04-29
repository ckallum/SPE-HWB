package com.example.healthproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthproject.Model.dto.Event;
import com.example.healthproject.R;
import com.example.healthproject.View.UserView;
import com.example.healthproject.View.ViewModelController;
import com.example.healthproject.View.ViewModelFactory;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateEvent extends AppCompatActivity {
    private ViewModelController updateViewModel;
    private EditText eventName;
    private EditText eventSpaces;
    private EditText eventDate;
    private EditText eventStart;
    private EditText eventEnd;
    private EditText description;
    private Button updateButton;

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
        Button deleteButton = findViewById(R.id.button_delete);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("events").document(Objects.requireNonNull(intent.getStringExtra("ID")));

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Event event = documentSnapshot.toObject(Event.class);
            assert event != null;
            eventName.setText(event.getName());
            eventSpaces.setText(String.valueOf(event.getAttendees()));
            eventDate.setText(event.getDate());
            eventStart.setText(event.getStart());
            eventEnd.setText(event.getEnd());
            description.setText(event.getDescription());
            eventVenue.setPrompt(event.getLocation());
        });

        final List<String> venues = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, venues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventVenue.setAdapter(adapter);
        db.collection("venues").get().addOnCompleteListener(task -> {
            if ( task.isSuccessful() ){
                for(QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())){
                    String v = document.getString("name");
                    venues.add(v);
                }
                adapter.notifyDataSetChanged();
            }
        });

        updateViewModel.getFormState().observe(this, formState -> {
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

        updateButton.setOnClickListener(v -> updateViewModel.updateEvent(eventName.getText().toString(), eventStart.getText().toString(), eventEnd.getText().toString(), eventDate.getText().toString(), eventSpaces.getText().toString(), eventVenue.getSelectedItem().toString(), description.getText().toString(), intent.getStringExtra("ID")));

        deleteButton.setOnClickListener(v -> {
            updateViewModel.deleteEvent(intent.getStringExtra("ID"));
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });
        updateViewModel.getAuthResult().observe(this, firebaseAuthResult -> {
            if (firebaseAuthResult == null){
                return;
            }
            if (firebaseAuthResult.getError() != null ){
                showFailed(firebaseAuthResult.getError());
            }
            if (firebaseAuthResult.getSuccess()!=null){
                showSuccess(firebaseAuthResult.getSuccess());
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
