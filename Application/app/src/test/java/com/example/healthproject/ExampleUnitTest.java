package com.example.healthproject;

import com.example.healthproject.Activity.RegisterActivity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        assertEquals(4, 2 + 2);
    }

    @Test
    public void password_isCorrectLength() {
        RegisterActivity myClass = new RegisterActivity();

        Boolean result = myClass.isValidPassword("Test1");
        Boolean expected = Boolean.FALSE;

        assertEquals(expected,result);
    }

    @Test
    public void password_hasSymbol() {
        RegisterActivity myClass = new RegisterActivity();

        Boolean result = myClass.isValidPassword("Test111");
        Boolean expected = Boolean.FALSE;

        assertEquals(expected,result);
    }

    @Test
    public void password_hasCap() {
        RegisterActivity myClass = new RegisterActivity();

        Boolean result = myClass.isValidPassword("test111");
        Boolean expected = Boolean.FALSE;

        assertEquals(expected,result);
    }





}

    