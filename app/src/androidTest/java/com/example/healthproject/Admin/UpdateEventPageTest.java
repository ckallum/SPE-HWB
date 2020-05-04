package com.example.healthproject.Admin;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import com.example.healthproject.Activity.LoginActivity;
import com.example.healthproject.Model.FirebaseDataSource;
import com.example.healthproject.Model.GlobalUser;
import com.example.healthproject.R;
import com.example.healthproject.TestUtils.MyViewAction;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class UpdateEventPageTest {

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
        GlobalUser.getInstance(new FirebaseDataSource()).login(adminEmail, true);
        onView(withId(R.id.manage)).perform(click());

    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    @Test
    public void testManageButtonClick() {
        onView(withId(R.id.eventRecycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.list_button_manage)));
        onView(withId(R.id.event_name)).check(matches(isDisplayed()));
        onView(withId(R.id.event_spaces)).check(matches(isDisplayed()));
        onView(withId(R.id.event_date)).check(matches(isDisplayed()));

        onView(withId(R.id.event_start)).check(matches(isDisplayed()));
        onView(withId(R.id.event_end)).check(matches(isDisplayed()));
        onView(withId(R.id.event_description)).check(matches(isDisplayed()));
        onView(withId(R.id.button_change)).check(matches(withText("Update")));
        onView(withId(R.id.button_delete)).check(matches(withText("Delete")));
    }

}
