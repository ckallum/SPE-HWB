package com.example.healthproject.Activity;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.example.healthproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UserLoginAndNavigationTest {

    @Rule
    public IntentsTestRule<LoginActivity> mActivityTestRule = new IntentsTestRule<>(LoginActivity.class);

    @Before
    public void setUp(){
        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }


    @Test
    public void eventsTest() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                childAtPosition(

                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("user@uobactive.ac.uk"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Users1"), closeSoftKeyboard());
        assert(!mActivityTestRule.getActivity().findViewById(R.id.username).toString().isEmpty());
        assert(!mActivityTestRule.getActivity().findViewById(R.id.password).toString().isEmpty());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.login), withText("Sign In "),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());
        Thread.sleep(2000L);

        intended(hasComponent(MainActivity.class.getName()));

        BottomNavigationView nav = mActivityTestRule.getActivity().findViewById(R.id.user_navbar);
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.booking), withContentDescription("Booking"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.user_navbar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());
        assert(nav.getMenu().findItem(R.id.booking).isChecked());


        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.events), withContentDescription("Events"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.user_navbar),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());
        assert(nav.getMenu().findItem(R.id.events).isChecked());

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.profile), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.user_navbar),
                                        0),
                                4),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());
        assert(nav.getMenu().findItem(R.id.profile).isChecked());

        ViewInteraction bottomNavigationItemView4 = onView(
                allOf(withId(R.id.venues), withContentDescription("Venues"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.user_navbar),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView4.perform(click());
        assert(nav.getMenu().findItem(R.id.venues).isChecked());


    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
