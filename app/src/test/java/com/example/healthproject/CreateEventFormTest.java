package com.example.healthproject;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.healthproject.View.ViewModelController;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class CreateEventFormTest {
    private ViewModelController testController;
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MutableLiveData form = new MutableLiveData<>();
        testController = new ViewModelController(form);
    }

    @Test
    public void testValidEvent(){
//        testController.eventDataChanged();
        assertFalse(testController.getFormState().getValue().getEventError());
    }

}
