package com.example.healthproject.Global;


import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.healthproject.Activity.LoginActivity;
import com.example.healthproject.Activity.RegisterActivity;
import com.example.healthproject.R;

import net.andreinc.mockneat.MockNeat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.not;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> activityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    static String userEmail = MockNeat.secure().emails().val();
    static final String userPassword = "Register1";
    static final String userPassword2 = "Register1";



    @Before
    public void setUp(){
        Intents.init();
    }

    @After
    public void cleanUp() {
        Intents.release();
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

        assertNotNull(activity.findViewById(R.id.et_password_retype));
        EditText passwordText2 = activity.findViewById(R.id.et_password_retype);
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
        onView(withId(R.id.et_email_register)).perform(typeText(userEmail)).perform(closeSoftKeyboard());
        onView(withId(R.id.et_password_register)).perform(typeText(userPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.et_password_retype)).perform(click(),replaceText(userPassword2)).perform(closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
        Thread.sleep(2000L);
        intended(hasComponent(LoginActivity.class.getName()));
        onView(withText("User Added")).
                inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView()))).
                check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testRegisterWithExistingCredentials() throws InterruptedException {
        onView(withId(R.id.et_email_register)).perform(typeText("registerTest@uobactive.ac.uk"));
        onView(withId(R.id.et_password_register)).perform(typeText(userPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.et_password_retype)).perform(replaceText(userPassword2)).perform(closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
        Thread.sleep(2000L);
        onView(withText("Register Failed")).
                inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView()))).
                check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testRegisterWithInValidCredentials() throws InterruptedException {
        onView(withId(R.id.et_email_register)).perform(typeText(userEmail));
        onView(withId(R.id.et_password_register)).perform(typeText(userPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.et_password_retype)).perform(replaceText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
        Thread.sleep(2000L);
    }


}
