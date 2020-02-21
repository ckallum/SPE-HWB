package com.example.healthproject.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthproject.Data.FirebaseDataSource;
import com.example.healthproject.Data.GlobalUser;
public class ViewModelFactory implements ViewModelProvider.Factory {

    /**
     * ViewModelController provider factory to instantiate ForgotViewModel.
     * Required given ForgotViewModel has a non-empty constructor
     */

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViewModelController.class)) {
            return (T) new ViewModelController(GlobalUser.getInstance(new FirebaseDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModelController class");
        }
    }
}

