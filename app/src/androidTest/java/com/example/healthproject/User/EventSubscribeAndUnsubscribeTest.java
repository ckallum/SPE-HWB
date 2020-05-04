package com.example.healthproject.User;

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
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class EventSubscribeAndUnsubscribeTest {

    static final String userEmail = "user@uobactive.ac.uk";
    static final String userPassword = "Users1";
    static final String testEventName = "Yoga with Tribe";

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        Intents.init();
        onView(withId(R.id.username)).perform(typeText(userEmail));
        onView(withId(R.id.password)).perform(typeText(userPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.login)).perform(click());
        Thread.sleep(2000L);
        GlobalUser.getInstance(new FirebaseDataSource()).login(userEmail, true);

    }

    @After
    public void cleanUp() {
        Intents.release();
        GlobalUser.getInstance(new FirebaseDataSource()).logout();
    }

    @Test
    public void testButtonSubscribe() throws InterruptedException {
        onView(withId(R.id.events)).perform(click());
        Thread.sleep(2000L);
        onView(withId(R.id.eventRecycler)).check(matches(MyViewAction.atPosition(0, hasDescendant(withText(testEventName))))).check(matches(isDisplayed()));
        onView(withId(R.id.eventRecycler)).check(matches(MyViewAction.atPosition(0, hasDescendant(withId(R.id.list_interested)))));
        onView(withId(R.id.eventRecycler)).check(matches(MyViewAction.atPosition(0, hasDescendant(withText("Interested: 0")))));
        onView(withId(R.id.eventRecycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.button_interested)));
        Thread.sleep(2000L);
        onView(withId(R.id.eventRecycler)).check(matches(MyViewAction.atPosition(0, hasDescendant(withText("Interested: 1")))));
    }

    @Test
    public void testButtonUnSubscribe() throws InterruptedException {
        onView(withId(R.id.booking)).perform(click());
        Thread.sleep(2000L);
        onView(withId(R.id.eventRecycler)).check(matches(MyViewAction.atPosition(0, hasDescendant(withText(testEventName))))).check(matches(isDisplayed()));
        onView(withId(R.id.eventRecycler)).check(matches(MyViewAction.atPosition(0, hasDescendant(withId(R.id.list_interested)))));
        onView(withId(R.id.list_interested)).check(matches(withText("Interested: 1")));
        onView(withId(R.id.eventRecycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.button_unsubscribe)));
        Thread.sleep(2000L);
        onView(withId(R.id.list_interested)).check(matches(withText("Interested: 0")));
    }

}
