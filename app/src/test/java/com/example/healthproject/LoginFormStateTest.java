
package com.example.healthproject;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.healthproject.View.ViewModelController;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


/**
 * Unit tests for Formstate validation Logic i.e. valid email, matching passwords, valid dates etc.
 */

@RunWith(JUnit4.class)

public class LoginFormStateTest {
    private ViewModelController testController;
    private String validEmail = "hello@gmail.com";
    private String invalidEmail = "@gmail.com";
    private String password1 = "Password1";
    private String invalidPassword = "";

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        MutableLiveData form = new MutableLiveData<>();
        testController = new ViewModelController(form);
    }

    @Test
    public void testValidLoginForm() {
        testController.loginDataChanged(validEmail, password1);
        assertTrue(testController.getFormState().getValue().getEmailError() == null && testController.getFormState().getValue().getPasswordError() == null);
        assertTrue(testController.getFormState().getValue().isDataValid());
    }

    @Test
    public void testInValidLoginForm1(){
        testController.loginDataChanged(invalidEmail, password1);
        assertFalse(testController.getFormState().getValue().getEmailError() == null && testController.getFormState().getValue().getPasswordError() == null);
        assertFalse(testController.getFormState().getValue().isDataValid());

    }



    @Test
    public void testInValidLoginForm2(){
        testController.loginDataChanged("", password1);
        assertFalse(testController.getFormState().getValue().getEmailError() == null && testController.getFormState().getValue().getPasswordError() == null);
        assertFalse(testController.getFormState().getValue().isDataValid());

    }

    @Test
    public void testInValidLoginForm3(){
        testController.loginDataChanged(validEmail, invalidPassword);
        assertFalse(testController.getFormState().getValue().getEmailError() == null && testController.getFormState().getValue().getPasswordError() == null);
        assertFalse(testController.getFormState().getValue().isDataValid());

    }

}
