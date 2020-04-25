package com.example.healthproject.View;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthproject.Model.FirebaseDataSource;
import com.example.healthproject.Model.GlobalUser;
import com.example.healthproject.Model.Result;
import com.example.healthproject.Model.dto.Event;
import com.example.healthproject.Model.dto.UserUpdateModel;
import com.example.healthproject.R;

import java.util.regex.Pattern;



public class ViewModelController extends ViewModel {

    private MutableLiveData<FormState> formState = new MutableLiveData<>();
    private MutableLiveData<FirebaseAuthResult> authResult = new MutableLiveData<>();
    private GlobalUser user;

    public ViewModelController(GlobalUser user) {
        this.user = user;
    }

    public LiveData<FormState> getFormState() {
        return formState;
    }

    public LiveData<FirebaseAuthResult> getAuthResult() {
        return authResult;
    }

    public void login(String username, Boolean admin) {
        // can be launched in a separate asynchronous job
        user.login(username, admin);
    }

    public void register(String email, String password) {
        // can be launched in a separate asynchronous job

        Result<UserUpdateModel> result = user.register(email, password);
        if (result instanceof Result.Success) {
            authResult.setValue(new FirebaseAuthResult(new UserView(((Result.Success) result).getData().toString())));
        } else {
            authResult.setValue(new FirebaseAuthResult(R.string.register_failed));
        }
    }

    public void forgot(String email) {
        // can be launched in a separate asynchronous job

        Result<UserUpdateModel> result = user.forgot(email);
        if (result instanceof Result.Success) {
            authResult.setValue(new FirebaseAuthResult(new UserView(((Result.Success) result).getData().toString())));

        } else {
            authResult.setValue(new FirebaseAuthResult(R.string.invalid_email));
        }
    }

    public void create_event(String name, String start, String end, String date, String spaces, String location, String description) {
        FirebaseDataSource dataSource = new FirebaseDataSource();
        Event event = new Event(0, 0, Integer.parseInt(spaces), date, name, location, start, end, description);
        dataSource.add_event(event);
        authResult.setValue(new FirebaseAuthResult(new UserView("Event Added")));
    }

    public void update_event(String name, String start, String end, String date, String spaces, String location, String description, String id) {
        FirebaseDataSource dataSource = new FirebaseDataSource();
        Event event = new Event(0, 0, Integer.parseInt(spaces), date, name, location, start, end, description);
        dataSource.changeEvent(event);
        authResult.setValue(new FirebaseAuthResult(new UserView("Event Updated")));
    }

    public void delete_event(String id ){
        FirebaseDataSource dataSource = new FirebaseDataSource();
        dataSource.deleteEvent(id);
        authResult.setValue(new FirebaseAuthResult(new UserView("Event Deleted")));

    }


    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            formState.setValue(new FormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            formState.setValue(new FormState(null, R.string.invalid_password));
        } else {
            formState.setValue(new FormState(true));
        }
    }

    public void eventDataChanged(String name, String start, String end, String date, String spaces) {
        if (!EventNameValid(name)) {
            formState.setValue(new FormState(R.string.event_name_error, null, null, null, null));
        } else if (!AttendeesValid(spaces)) {
            formState.setValue(new FormState(null, null, null, null, R.string.attendee_error));
        }
        else if (!DateValid(date)){
            formState.setValue(new FormState(null, null, null ,R.string.date_invalid, null));
        }
        else if (!TimeValid(start)) {
            formState.setValue(new FormState(null, R.string.event_time_error, null, null, null));
        }
        else if (!TimeValid(end)) {
            formState.setValue(new FormState(null, null,  R.string.event_time_error, null, null));
        }
        else {
            formState.setValue(new FormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    public void registerDataChanged(String username, String password, String password2) {
        if (!isUserNameValid(username)) {
            formState.setValue(new FormState(R.string.fui_invalid_email_address, null));
        } else if (!isPasswordValid(password, password2)) {
            formState.setValue(new FormState(null, R.string.invalid_password));
        } else {
            formState.setValue(new FormState(true));
        }
    }

    public void forgotDataChanged(String email) {
        if (!isUserNameValid(email)) {
            formState.setValue(new FormState(R.string.fui_invalid_email_address));
        } else {
            formState.setValue(new FormState(true));
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isPasswordValid(String password2, String password) {
        return password != null && password.trim().length() > 5 && password.equals(password2);
    }

    private boolean TimeValid(String timestamp) {
        Log.d("TimeReg", String.valueOf(timestamp.matches("^(2[0-3]|[0-1][0-9]|[0-9]):([0-5][0-9]|[0-9])$")));
        Log.d("Time", timestamp);
        return timestamp.matches("^(2[0-3]|[0-1][0-9]|[0-9]):([0-5][0-9]|[0-9])$");
    }

    private boolean DateValid(String date) {
        Log.d("Date", date);
        Log.d("DateReg", String.valueOf(date.matches("^([0-2][0-9]|3[0-1]|[0-9])/(0[0-9]|1[0-2]|[0-9])/([0-9][0-9])?[0-9][0-9]$")));
        return date.matches("^([0-2][0-9]|3[0-1]|[0-9])/(0[0-9]|1[0-2]|[0-9])/([0-9][0-9])?[0-9][0-9]$");
    }

    private boolean EventNameValid(String name) {
        return name != null && !name.isEmpty();
    }

    private boolean AttendeesValid(String number) {
        return number != null && number_matches(number);

    }

    private boolean number_matches(String number){
        if (number.isEmpty() ){
            return false;
        }
        Pattern pattern = Pattern.compile("^\\d+$");
        return pattern.matcher(number).matches();
    }

}