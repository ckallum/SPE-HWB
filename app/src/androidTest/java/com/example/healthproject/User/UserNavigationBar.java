package com.example.healthproject.User;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UserNavigationBar {
    static final String userEmail = "user@uobactive.ac.uk";
    static final String userPassword = "Users1";

    @Rule
    public ActivityTestRule<NavigationActivity> activityTestRule = new ActivityTestRule<>(NavigationActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        Intents.init();
        GlobalUser.getInstance(new FirebaseDataSource()).login(userEmail, false);
    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    @Test
    public void testLogin(){
        GlobalUser user = GlobalUser.getInstance(new FirebaseDataSource());
        assertTrue(user.isLoggedIn());
        assertFalse(user.isAdmin());
    }

    @Test
    public void testNavBarUi(){
        onView(withId(R.id.user_navbar)).check(matches(isDisplayed()));
        onView(withId(R.id.home)).check(matches(isDisplayed()));
        onView(withId(R.id.booking)).check(matches(isDisplayed()));
        onView(withId(R.id.venues)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
        onView(withId(R.id.events)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavBarInteraction(){
        onView(withId(R.id.home)).perform(click());
        onView(withId(R.id.fragment_home)).check(matches(isDisplayed()));
        onView(withId(R.id.booking)).perform(click());
        onView(withId(R.id.fragment_bookings)).check(matches(isDisplayed()));
        onView(withId(R.id.venues)).perform(click());
        onView(withId(R.id.fragment_venues)).check(matches(isDisplayed()));
        onView(withId(R.id.events)).perform(click());
        onView(withId(R.id.fragment_events)).check(matches(isDisplayed()));
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.id.fragment_profile)).check(matches(isDisplayed()));
    }
}
