package com.example.healthproject;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.healthproject.View.ViewModelController;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class CreateEventFormTest {
    private ViewModelController testController;
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    private String eventName = "Test Event";
    private String date = "19/02/2020";
    private String start = "17:00";
    private String end = "18:00";
    private String attendees = "1";

    @Before
    public void setUp() {
        MutableLiveData form = new MutableLiveData<>();
        testController = new ViewModelController(form);
    }

    @Test
    public void testValidEvent(){
        testController.eventDataChanged(eventName, start, end, date, attendees);
        assertTrue(testController.getFormState().getValue().isDataValid());
    }

    @Test
    public void testInvalidAttendees(){
        testController.eventDataChanged(eventName, start, end, date, "");
        assertFalse(testController.getFormState().getValue().isDataValid());
        assertNotNull(testController.getFormState().getValue().getEventAttendeeError());
    }

    @Test
    public void testInvalidDate(){
        testController.eventDataChanged(eventName, start, end, "", attendees);
        assertFalse(testController.getFormState().getValue().isDataValid());
        assertNotNull(testController.getFormState().getValue().getEventDateError());
    }

    @Test
    public void testInvalidStart(){
        testController.eventDataChanged(eventName, "", end, date, attendees);
        assertFalse(testController.getFormState().getValue().isDataValid());
        assertNotNull(testController.getFormState().getValue().getEventStartTimeError());

    }

    @Test
    public void testInvalidEnd(){
        testController.eventDataChanged(eventName, start, "", date, attendees);
        assertFalse(testController.getFormState().getValue().isDataValid());
        assertNotNull(testController.getFormState().getValue().getEventEndTimeError());

    }

    @Test
    public void testInvalidName(){
        testController.eventDataChanged("", start, end, date, attendees);
        assertFalse(testController.getFormState().getValue().isDataValid());
        assertNotNull(testController.getFormState().getValue().getEventNameError());

    }

}
