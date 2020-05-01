package com.example.healthproject.View;

import android.util.Log;

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

    ViewModelController(GlobalUser user) {
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

    public void register(){
        user.register();
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

    public void createEvent(String name, String start, String end, String date, String spaces, String location, String description) {
        FirebaseDataSource dataSource = new FirebaseDataSource();
        Event event = new Event(0, 0, Integer.parseInt(spaces), date, name, location, start, end, description);
        dataSource.addEvent(event);
        authResult.setValue(new FirebaseAuthResult(new UserView("Event Added")));
    }

    public void updateEvent(String name, String start, String end, String date, String spaces, String location, String description, String id) {
        FirebaseDataSource dataSource = new FirebaseDataSource();
        Event event = new Event(0, 0, Integer.parseInt(spaces), date, name, location, start, end, description);
        event.setId(id);
        dataSource.changeEvent(event);
        authResult.setValue(new FirebaseAuthResult(new UserView("Event Updated")));
    }

    public void deleteEvent(String id ){
        FirebaseDataSource dataSource = new FirebaseDataSource();
        dataSource.deleteEvent(id);
        authResult.setValue(new FirebaseAuthResult(new UserView("Event Deleted")));

    }


    public void loginDataChanged(String username, String password) {
        if (!isEmailValid(username)) {
            formState.setValue(new FormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            formState.setValue(new FormState(null, R.string.invalid_password));
        } else {
            formState.setValue(new FormState(true));
        }
    }

    public void eventDataChanged(String name, String start, String end, String date, String spaces) {
        if (!eventNameValid(name)) {
            formState.setValue(new FormState(R.string.event_name_error, null, null, null, null));
        } else if (!attendeesValid(spaces)) {
            formState.setValue(new FormState(null, null, null, null, R.string.attendee_error));
        }
        else if (!dateValid(date)){
            formState.setValue(new FormState(null, null, null ,R.string.date_invalid, null));
        }
        else if (!timeValid(start)) {
            formState.setValue(new FormState(null, R.string.event_time_error, null, null, null));
        }
        else if (!timeValid(end)) {
            formState.setValue(new FormState(null, null,  R.string.event_time_error, null, null));
        }
        else {
            formState.setValue(new FormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isEmailValid(String username) {

        return username!=null && !username.isEmpty()&&  username.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
    }

    public void registerDataChanged(String username, String password, String password2) {
        if (!isEmailValid(username)) {
            formState.setValue(new FormState(R.string.fui_invalid_email_address, null));
        } else if (!isPasswordValid(password, password2)) {
            formState.setValue(new FormState(null, R.string.invalid_password));
        } else {
            formState.setValue(new FormState(true));
        }
    }

    public void forgotDataChanged(String email) {
        if (!isEmailValid(email)) {
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
        return password != null && password.trim().length() > 5 && password2 != null && password2.trim().length() > 5 && password.equals(password2);
    }

    private boolean timeValid(String timestamp) {
        Log.d("TimeReg", String.valueOf(timestamp.matches("^(2[0-3]|[0-1][0-9]|[0-9]):([0-5][0-9]|[0-9])$")));
        Log.d("Time", timestamp);
        return timestamp.matches("^(2[0-3]|[0-1][0-9]|[0-9]):([0-5][0-9]|[0-9])$");
    }

    private boolean dateValid(String date) {
        Log.d("Date", date);
        Log.d("DateReg", String.valueOf(date.matches("^([0-2][0-9]|3[0-1]|[0-9])/(0[0-9]|1[0-2]|[0-9])/([0-9][0-9])?[0-9][0-9]$")));
        return date.matches("^([0-2][0-9]|3[0-1]|[0-9])/(0[0-9]|1[0-2]|[0-9])/([0-9][0-9])?[0-9][0-9]$");
    }

    private boolean eventNameValid(String name) {
        return name != null && !name.isEmpty();
    }

    private boolean attendeesValid(String number) {
        return number != null && numberMatches(number);

    }

    private boolean numberMatches(String number){
        if (number.isEmpty() ){
            return false;
        }
        Pattern pattern = Pattern.compile("^\\d+$");
        return pattern.matcher(number).matches();
    }

}
