package com.example.healthproject.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthproject.R;
import com.example.healthproject.View.FirebaseAuthResult;
import com.example.healthproject.View.FormState;
import com.example.healthproject.View.UserView;
import com.example.healthproject.View.ViewModelController;
import com.example.healthproject.View.ViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class CreateEventFragment extends Fragment {
    private ViewModelController createViewModel;
    private EditText eventName;
    private EditText eventSpaces;
    private EditText eventDate;
    private EditText eventStart;
    private EditText eventEnd;
    private EditText description;
    private Button createButton;
    private DatePickerDialog dPicker;
    private TimePickerDialog tPickerStart;
    private TimePickerDialog tPickerEnd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create, container, false );
        FirebaseFirestore mdb = FirebaseFirestore.getInstance();
        CollectionReference ref = mdb.collection("venues");
        createViewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(ViewModelController.class);
        eventName = root.findViewById(R.id.event_name);
        eventSpaces= root.findViewById(R.id.event_spaces);//
        final Spinner eventVenue = root.findViewById(R.id.event_location);
        eventDate = root.findViewById(R.id.event_date);
        eventStart = root.findViewById(R.id.event_start);
        eventEnd = root.findViewById(R.id.event_end);
        description = root.findViewById(R.id.event_description);
        createButton = root.findViewById(R.id.button_create);


        createViewModel.getFormState().observe(getViewLifecycleOwner(), new Observer<FormState>() {
            @Override
            public void onChanged(FormState formState) {
                if (formState == null) {
                    return;
                }
                createButton.setEnabled(formState.isDataValid());
                if (formState.getEventNameError() != null) {
                    eventName.setError(getString(formState.getEventNameError()));
                }
                if (formState.getEventDateError() != null) {
                    eventDate.setError(getString(formState.getEventDateError()));
                }
                if (formState.getEventStartTimeError() != null) {
                    eventStart.setError(getString(formState.getEventStartTimeError()));
                }
                if (formState.getEventEndTimeError() != null) {
                    eventEnd.setError(getString(formState.getEventEndTimeError()));
                }
                if (formState.getEventAttendeeError() != null){
                    eventSpaces.setError(getString(formState.getEventAttendeeError()));
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                createViewModel.eventDataChanged(eventName.getText().toString(), eventStart.getText().toString(), eventEnd.getText().toString(), eventDate.getText().toString(), eventSpaces.getText().toString());
            }
        };
        eventName.addTextChangedListener(afterTextChangedListener);
        eventStart.addTextChangedListener(afterTextChangedListener);
        eventSpaces.addTextChangedListener(afterTextChangedListener);
        eventEnd.addTextChangedListener(afterTextChangedListener);
        eventDate.addTextChangedListener(afterTextChangedListener);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createViewModel.create_event(eventName.getText().toString(), eventStart.getText().toString(), eventEnd.getText().toString(), eventDate.getText().toString(), eventSpaces.getText().toString(), eventVenue.getSelectedItem().toString(), description.getText().toString());
            }
        });

        createViewModel.getAuthResult().observe(getViewLifecycleOwner(), new Observer<FirebaseAuthResult>() {
            @Override
            public void onChanged(FirebaseAuthResult firebaseAuthResult) {
                if (firebaseAuthResult == null){
                    return;
                }
                if (firebaseAuthResult.getError() != null ){
                    showCreationFailed(firebaseAuthResult.getError());
                }
                if (firebaseAuthResult.getSuccess()!=null){
                    showCreationSuccess(firebaseAuthResult.getSuccess());
                }
            }
        });


        final List<String> venues = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, venues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventVenue.setAdapter(adapter);
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if ( task.isSuccessful() ){
                    for(QueryDocumentSnapshot document:task.getResult()){
                        String v = document.getString("name");
                        venues.add(v);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        eventDate.setInputType(InputType.TYPE_NULL);
        eventDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                dPicker = new DatePickerDialog(requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eventDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                dPicker.show();
            }
        });
        eventStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                tPickerStart = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eventStart.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
               tPickerStart.show();

            }
        });
        eventEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                tPickerEnd = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        eventEnd.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                tPickerEnd.show();

            }
        });

        return root;
    }

    private void showCreationSuccess(UserView success) {
        Toast.makeText(getContext(), success.getString(), Toast.LENGTH_SHORT).show();
    }

    private void showCreationFailed(@StringRes Integer errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }



}
