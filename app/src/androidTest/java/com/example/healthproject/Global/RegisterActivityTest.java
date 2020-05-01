package com.example.healthproject.Global;


import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.healthproject.Activity.LoginActivity;
import com.example.healthproject.Activity.RegisterActivity;
import com.example.healthproject.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> activityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    static final String userEmail = "user@uobactive.ac.uk";
    static final String userPassword = "Users1";
    static final String userPassword2 = "Users1";



    @Before
    public void setUp(){
        Intents.init();
    }

    @Test
    public void testUiLayout() {
        Activity activity = activityTestRule.getActivity();
        assertNotNull(activity.findViewById(R.id.et_email_register));
        EditText emailText = activity.findViewById(R.id.et_email_register);
        assertTrue(emailText.isShown());

        assertNotNull(activity.findViewById(R.id.et_password_register));
        EditText passwordText = activity.findViewById(R.id.et_password_register);
        assertTrue(passwordText.isShown());

        assertNotNull(activity.findViewById(R.id.et_password_register2));
        EditText passwordText2 = activity.findViewById(R.id.et_password_register2);
        assertTrue(passwordText2.isShown());

        assertNotNull(activity.findViewById(R.id.signInLink));
        assertNotNull(activity.findViewById(R.id.registerButton));

        TextView forgotText = activity.findViewById(R.id.signInLink);
        assertEquals("Sign in", forgotText.getText().toString());

        Button registerButton = activity.findViewById(R.id.registerButton);
        assertEquals("Sign Up", registerButton.getText().toString());
    }

    @Test
    public void testSignInButtonPress(){
        onView(withId(R.id.signInLink)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void testRegisterWithValidCredentials() throws InterruptedException {
        onView(withId(R.id.et_email_register)).perform(typeText(userEmail));
        onView(withId(R.id.et_password_register)).perform(typeText(userPassword));
        onView(withId(R.id.et_password_register2)).perform(typeText(userPassword2)).perform(closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
        Thread.sleep(2000L);
        intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void testRegisterWithInValidCredentials(){
        onView(withId(R.id.et_email_register)).perform(typeText(userEmail));
        onView(withId(R.id.et_password_register)).perform(typeText(userPassword));
        onView(withId(R.id.et_password_register2)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
        assertEquals("RegisterActivity", activityTestRule.getActivity().getClass().getName());
    }
}
