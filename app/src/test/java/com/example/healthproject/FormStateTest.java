
package com.example.healthproject;

import android.util.Patterns;

import com.example.healthproject.View.ViewModelController;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;


/**
 * Unit tests for Formstate validation Logic i.e. valid email, matching passwords, valid dates etc.
 */

public class FormStateTest {
    private ViewModelController testController;

    @Before
    public void setUp(){
//        testController = ViewModelProviders.of(this, new ViewModelFactory()).get(ViewModelController.class);

    }


    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(Patterns.EMAIL_ADDRESS.matcher("name@email.com").matches());
    }

    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(Patterns.EMAIL_ADDRESS.matcher("name@email.co.uk").matches());
    }

    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(Patterns.EMAIL_ADDRESS.matcher("name@email").matches());
    }

    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(Patterns.EMAIL_ADDRESS.matcher("name@email..com").matches());
    }

    @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(Patterns.EMAIL_ADDRESS.matcher("@email.com").matches());
    }

    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(Patterns.EMAIL_ADDRESS.matcher("").matches());
    }

    @Test
    public void emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(Patterns.EMAIL_ADDRESS.matcher(null).matches());
    }
}
