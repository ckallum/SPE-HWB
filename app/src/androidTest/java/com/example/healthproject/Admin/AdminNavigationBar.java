package com.example.healthproject.Admin;

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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertTrue;

public class AdminNavigationBar {
    // Login with preset admin nav details
    static final String adminEmail = "admin@uobactive.ac.uk";
    static final String adminPassword = "Admin1";

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        Intents.init();
        onView(withId(R.id.username)).perform(typeText(adminEmail));
        onView(withId(R.id.password)).perform(typeText(adminPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2000L);

    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    @Test
    public void testLogin(){
        intended(hasComponent(NavigationActivity.class.getName()));
        GlobalUser user = GlobalUser.getInstance(new FirebaseDataSource());
        assertTrue(user.isLoggedIn());
        assertTrue(user.isAdmin());
    }

    @Test
    public void testNavBarUi(){
        onView(withId(R.id.admin_navbar)).check(matches(isDisplayed()));
        onView(withId(R.id.home)).check(matches(isDisplayed()));
        onView(withId(R.id.add)).check(matches(isDisplayed()));
        onView(withId(R.id.manage)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavBarInteraction(){
        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.fragment_create)).check(matches(isDisplayed()));
        onView(withId(R.id.home)).perform(click());
        onView(withId(R.id.fragment_home)).check(matches(isDisplayed()));
        onView(withId(R.id.manage)).perform(click());
        onView(withId(R.id.fragment_manage)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.id.fragment_profile)).check(matches(isDisplayed()));
    }
}
