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

import com.example.healthproject.Activity.ForgotActivity;
import com.example.healthproject.Activity.LoginActivity;
import com.example.healthproject.Activity.MainActivity;
import com.example.healthproject.Activity.RegisterActivity;
import com.example.healthproject.Model.FirebaseDataSource;
import com.example.healthproject.Model.GlobalUser;
import com.example.healthproject.R;

import org.junit.After;
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
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.not;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    static final String userEmail = "user@uobactive.ac.uk";
    static final String userPassword = "Users1";
    static final String adminEmail = "admin@uobactive.ac.uk";
    static final String adminPassword = "Admin1";
    static final String invalidEmail = "invalid@uobactive.ac.uk";
    static final String invalidPassword = "Invalid1";

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
        assertNotNull(activity.findViewById(R.id.username));
        EditText emailText = activity.findViewById(R.id.username);
        assertTrue(emailText.isShown());

        assertNotNull(activity.findViewById(R.id.password));
        EditText passwordText = activity.findViewById(R.id.password);
        assertTrue(passwordText.isShown());

        assertNotNull(activity.findViewById(R.id.forgotPassLink));
        assertNotNull(activity.findViewById(R.id.signUpLink));
        assertNotNull(activity.findViewById(R.id.login));

        TextView forgotText = activity.findViewById(R.id.forgotPassLink);
        assertEquals("Forgotten Password?", forgotText.getText().toString());

        TextView registerText = activity.findViewById(R.id.signUpLink);
        assertEquals("Don't have an Account? Click Here!", registerText.getText().toString());

        Button loginButton = activity.findViewById(R.id.login);
        assertEquals("Sign in", loginButton.getText().toString());
    }

    @Test
    public void testForgotButtonPress(){
        onView(withId(R.id.forgotPassLink)).perform(click());
        intended(hasComponent(ForgotActivity.class.getName()));
    }

    @Test
    public void testRegisterButtonPress(){
        onView(withId(R.id.signUpLink)).perform(click());
        intended(hasComponent(RegisterActivity.class.getName()));
    }

    @Test
    public void signInWithUserTest() throws InterruptedException {
        onView(withId(R.id.username)).perform(typeText(userEmail));
        onView(withId(R.id.password)).perform(typeText(userPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2000L);
        intended(hasComponent(MainActivity.class.getName()));
        GlobalUser user = GlobalUser.getInstance(new FirebaseDataSource());
        assertTrue(user.isLoggedIn());
        assertFalse(user.isAdmin());
        onView(withText("Welcome ! User1")).
                inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView()))).
                check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void signInWithAdminTest() throws InterruptedException {
        onView(withId(R.id.username)).perform(typeText(adminEmail));
        onView(withId(R.id.password)).perform(typeText(adminPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2000L);
        intended(hasComponent(MainActivity.class.getName()));
        GlobalUser user = GlobalUser.getInstance(new FirebaseDataSource());
        assertTrue(user.isLoggedIn());
        assertTrue(user.isAdmin());
        onView(withText("Welcome ! Admin")).
                inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView()))).
                check(ViewAssertions.matches(isDisplayed()));

    }

    @Test
    public void signInWithInvalidCredentialTest() throws InterruptedException {
        onView(withId(R.id.username)).perform(typeText(invalidEmail));
        onView(withId(R.id.password)).perform(typeText(invalidPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2000L);
        GlobalUser user = GlobalUser.getInstance(new FirebaseDataSource());
        assertFalse(user.isLoggedIn());
        onView(withText("Login failed")).
                inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView()))).
                check(ViewAssertions.matches(isDisplayed()));
    }

}
