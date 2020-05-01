
package com.example.healthproject.Global;

import androidx.lifecycle.MutableLiveData;

import com.example.healthproject.View.FormState;
import com.example.healthproject.View.ViewModelController;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;


/**
 * Unit tests for Formstate validation Logic i.e. valid email, matching passwords, valid dates etc.
 */

public class RegisterFormStateTest {
    private ViewModelController testController;
    private MutableLiveData<FormState> formState;

    @Before
    public void setUp(){
        formState = new MutableLiveData<>();
    }


    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assert("name@email.com".matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"));
    }

    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assert("name@email.co.uk".matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"));
    }


    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse("name@email..com".matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"));
    }

    @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assert(!"@email.com".matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"));
    }

    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse("".matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"));
    }

}
