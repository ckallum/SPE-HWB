package com.example.healthproject.Global;

import android.app.Activity;
import android.widget.Button;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import com.example.healthproject.Activity.Navigation.NavigationActivity;
import com.example.healthproject.Model.FirebaseDataSource;
import com.example.healthproject.Model.GlobalUser;
import com.example.healthproject.R;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.model.Atoms.getCurrentUrl;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class HomeActivityTest {
    @Rule
    public ActivityTestRule<NavigationActivity> activityTestRule = new ActivityTestRule<>(NavigationActivity.class);

    static final String userEmail = "user@uobactive.ac.uk";
    static final String userPassword = "Users1";

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
    public void testUiLayout() {
        Activity activity = activityTestRule.getActivity();

        assertNotNull(activity.findViewById(R.id.stepLink));
        Button stepTracker = activity.findViewById(R.id.stepLink);
        assertTrue(stepTracker.isShown());
        assertEquals("Step Tracker", stepTracker.getText().toString());

        assertNotNull(activity.findViewById(R.id.newsLink));
        Button news = activity.findViewById(R.id.newsLink);
        assertTrue(stepTracker.isShown());
        assertEquals("News", news.getText().toString());

        assertNotNull(activity.findViewById(R.id.twitterLink));
        Button twitter = activity.findViewById(R.id.twitterLink);
        assertTrue(stepTracker.isShown());
        assertEquals("Twitter", twitter.getText().toString());

        assertNotNull(activity.findViewById(R.id.instaLink));
        Button instagram = activity.findViewById(R.id.instaLink);
        assertTrue(stepTracker.isShown());
        assertEquals("Instagram", instagram.getText().toString());

        assertNotNull(activity.findViewById(R.id.membersLink));
        Button membership = activity.findViewById(R.id.membersLink);
        assertTrue(stepTracker.isShown());
        assertEquals("Membership", membership.getText().toString());

        assertNotNull(activity.findViewById(R.id.societiesLink));
        Button su = activity.findViewById(R.id.societiesLink);
        assertTrue(stepTracker.isShown());
        assertEquals("Clubs and societies", su.getText().toString());
    }

    @Test
    public void testNewsButtonPress() throws InterruptedException {
        onView(withId(R.id.newsLink)).perform(click());
        onView(withId(R.id.newsPage)).perform(click());
        Thread.sleep(1000L);
        onWebView().check(webMatches(getCurrentUrl(), Matchers.containsString("http://www.bristol.ac.uk/sport/news/")));

    }

    @Test
    public void testStepButtonPress() throws InterruptedException {
        onView(withId(R.id.stepLink)).perform(click());
        onView(withId(R.id.stepGoal)).check(matches(isDisplayed()));
    }

    @Test
    public void testTwitterButtonPress() throws InterruptedException {
        onView(withId(R.id.twitterLink)).perform(click());
        onView(withId(R.id.twitterPage)).perform(click());
        Thread.sleep(1000L);
        onWebView().check(webMatches(getCurrentUrl(), Matchers.containsString("https://twitter.com/BristolUniSport")));

    }

    @Test
    public void testInstagramButtonPress() throws InterruptedException {
        onView(withId(R.id.instaLink)).perform(click());
        onView(withId(R.id.instPage)).perform(click());
        Thread.sleep(1000L);
        onWebView().check(webMatches(getCurrentUrl(), Matchers.containsString("https://www.instagram.com/bristolunisport/?hl=en")));

    }

    @Test
    public void testMembershipButtonPress() throws InterruptedException {
        onView(withId(R.id.membersLink)).perform(click());
        onView(withId(R.id.membersPage)).perform(click());
        Thread.sleep(1000L);
        onWebView().check(webMatches(getCurrentUrl(), Matchers.containsString("http://www.bristol.ac.uk/sport/memberships/")));

    }

    @Test
    public void testSuButtonPress() throws InterruptedException {
        onView(withId(R.id.societiesLink)).perform(click());
        onView(withId(R.id.societiesPage)).perform(click());
        Thread.sleep(1000L);
        onWebView().check(webMatches(getCurrentUrl(), Matchers.containsString("https://www.bristolsu.org.uk/sports-clubs-societies")));
    }

}
