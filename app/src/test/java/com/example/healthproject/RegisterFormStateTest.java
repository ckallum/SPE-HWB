
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
import static org.junit.Assert.assertTrue;


/**
 * Unit tests for Formstate validation Logic i.e. valid email, matching passwords, valid dates etc.
 */

@RunWith(JUnit4.class)
public class RegisterFormStateTest {
    private ViewModelController testController;
    private String validEmail = "hello@gmail.com";
    private String invalidEmail = "@gmail.com";
    private String password1 = "Password1";
    private String password2 = "Password1";
    private String invalidPassword = "Password";

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MutableLiveData form = new MutableLiveData<>();
        testController = new ViewModelController(form);
    }


    @Test
    public void testValidRegisterForm() {
        testController.registerDataChanged(validEmail, password1, password2);
        assertTrue(testController.getFormState().getValue().getEmailError() == null && testController.getFormState().getValue().getPasswordError() == null);

    }

    @Test
    public void testInValidRegisterForm1() {
        testController.registerDataChanged(invalidEmail, password1, password2);
        assertFalse(testController.getFormState().getValue().getEmailError() == null && testController.getFormState().getValue().getPasswordError() == null);

    }

    @Test
    public void testInValidRegisterForm2() {
        testController.registerDataChanged(validEmail, password1, invalidPassword);
        assertFalse(testController.getFormState().getValue().getEmailError() == null && testController.getFormState().getValue().getPasswordError() == null);

    }

    @Test
    public void testInValidRegisterForm3() {
        testController.registerDataChanged("", "", "");
        assertFalse(testController.getFormState().getValue().getEmailError() == null && testController.getFormState().getValue().getPasswordError() == null);

    }

}
