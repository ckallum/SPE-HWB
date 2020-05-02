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

public class VenuesTest {

    static final String userEmail = "user@uobactive.ac.uk";

    @Rule
    public ActivityTestRule<NavigationActivity> activityTestRule = new ActivityTestRule<>(NavigationActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        Intents.init();
        GlobalUser user = GlobalUser.getInstance(new FirebaseDataSource());
        user.login(userEmail, false);
    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    @Test
    public void testUi(){
        onView(withId(R.id.venues)).perform(click());
        //Test images are displayed
        onView(withId(R.id.indoor)).check(matches(isDisplayed()));
        onView(withId(R.id.coombe)).check(matches(isDisplayed()));
        onView(withId(R.id.richmond)).check(matches(isDisplayed()));
    }

    @Test
    public void imageViewPressTest(){
        onView(withId(R.id.venues)).perform(click());
        onView(withId(R.id.indoor)).perform(click());
        onView(withId(R.id.fragment_indoor)).check(matches(isDisplayed()));
        onView(withId(R.id.back)).perform(click());
        onView(withId(R.id.coombe)).perform(click());
        onView(withId(R.id.fragment_coombe)).check(matches(isDisplayed()));
        onView(withId(R.id.back)).perform(click());
        onView(withId(R.id.richmond)).perform(click());
        onView(withId(R.id.fragment_su)).check(matches(isDisplayed()));
    }

}
