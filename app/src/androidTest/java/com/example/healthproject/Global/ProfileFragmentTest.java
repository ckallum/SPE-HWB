package com.example.healthproject.Global;

import android.app.Activity;
import android.widget.Button;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import com.example.healthproject.Activity.LoginActivity;
import com.example.healthproject.Activity.Navigation.NavigationActivity;
import com.example.healthproject.Model.FirebaseDataSource;
import com.example.healthproject.Model.GlobalUser;
import com.example.healthproject.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class ProfileFragmentTest {
    @Rule
    public ActivityTestRule<NavigationActivity> activityTestRule = new ActivityTestRule<>(NavigationActivity.class);

    static final String userEmail = "user@uobactive.ac.uk";
    static final String userPassword = "Users1";
    static final String updatedName = "User1";
    static final String updatedPassword = "Users1";

    @Before
    public void setUp() {
        Intents.init();
        GlobalUser.getInstance(new FirebaseDataSource()).login(userEmail, false);
        onView(withId(R.id.profile)).perform(click());
    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    @Test
    public void testUiLayout() {
        Activity activity = activityTestRule.getActivity();

        onView(withId(R.id.cameraImage)).check(matches(isDisplayed()));
        onView(withId(R.id.usernameBox)).check(matches(isDisplayed()));
        onView(withId(R.id.emailBox)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordBox)).check(matches(isDisplayed()));
        onView(withId(R.id.button_save)).check(matches(isDisplayed()));
        onView(withId(R.id.logoutBtn)).check(matches(isDisplayed()));


        Button saveButton = activity.findViewById(R.id.button_save);
        assertEquals("Save", saveButton.getText().toString());

        Button logoutButton = activity.findViewById(R.id.logoutBtn);
        assertEquals("Log Out", logoutButton.getText().toString());


    }

    @Test
    public void testLogout() throws InterruptedException {
        onView(withId(R.id.logoutBtn)).perform(click());
        Thread.sleep(2000L);
        intended(hasComponent(LoginActivity.class.getName()));
        assertFalse(GlobalUser.getInstance(new FirebaseDataSource()).isLoggedIn());
    }
}
