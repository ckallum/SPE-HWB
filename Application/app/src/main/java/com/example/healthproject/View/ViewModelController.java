package com.example.healthproject.View;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthproject.Data.FirebaseDataSource;
import com.example.healthproject.Data.GlobalUser;
import com.example.healthproject.Data.Result;
import com.example.healthproject.Data.model.LoggedInUser;
import com.example.healthproject.Data.model.UserUpdateModel;
import com.example.healthproject.R;


public class ViewModelController extends ViewModel {

    private MutableLiveData<FormState> formState = new MutableLiveData<>();
    private MutableLiveData<FirebaseAuthResult> authResult = new MutableLiveData<>();
    private GlobalUser globalUser;
    private FirebaseDataSource data;

    public ViewModelController(GlobalUser globalUser) {
        this.globalUser = globalUser;
    }

    public LiveData<FormState> getFormState() {
        return formState;
    }

    public LiveData<FirebaseAuthResult> getAuthResult() {
        return authResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = globalUser.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            authResult.setValue(new FirebaseAuthResult(new UserView(data.getDisplayName())));
        } else {
            authResult.setValue(new FirebaseAuthResult(R.string.login_failed));
        }
    }

    public void register(String email, String password) {
        // can be launched in a separate asynchronous job

        Result<UserUpdateModel> result = data.register(email, password);
        if (result instanceof Result.Success) {
            authResult.setValue(new FirebaseAuthResult(new UserView(((Result.Success) result).getData().toString())));
        } else {
            authResult.setValue(new FirebaseAuthResult(R.string.login_failed));
        }
    }

    public void forgot(String email) {
        // can be launched in a separate asynchronous job

        Result<UserUpdateModel> result = data.forgot(email);
        if (result instanceof Result.Success) {
            authResult.setValue(new FirebaseAuthResult(new UserView(((Result.Success) result).getData().toString())));

        } else {
            authResult.setValue(new FirebaseAuthResult(R.string.login_failed));
        }
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
        }else{
            formState.setValue(new FormState(true));
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isPasswordValid(String password, String password2) {
        return password != null && password.trim().length() > 5 && password.equals(password2);
    }
}
