package com.example.healthproject.Global;


import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.healthproject.Activity.ForgotActivity;
import com.example.healthproject.Activity.LoginActivity;
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
import static androidx.test.espresso.intent.Intents.init;
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
public class ForgotActivityTest {

    @Rule
    public ActivityTestRule<ForgotActivity> activityTestRule = new ActivityTestRule<>(ForgotActivity.class);

    @Before
    public void setUp(){
        init();
    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    @Test
    public void testUI() {
        Activity activity = activityTestRule.getActivity();
        assertNotNull(activity.findViewById(R.id.emailTxtBox));
        EditText emailText = activity.findViewById(R.id.emailTxtBox);
        assertTrue(emailText.isShown());

        assertNotNull(activity.findViewById(R.id.backButton));
        assertNotNull(activity.findViewById(R.id.sendEmail));

        Button backButton = activity.findViewById(R.id.backButton);
        assertEquals("Back", backButton.getText().toString());

        Button saveButton = activity.findViewById(R.id.sendEmail);
        assertEquals("Send", saveButton.getText().toString());
    }

    @Test
    public void testBackButton(){
        onView(withId(R.id.backButton)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void testSendButton(){
        onView(withId(R.id.emailTxtBox)).perform(typeText("user@uobactive.ac.uk")).perform(closeSoftKeyboard());
        onView(withId(R.id.sendEmail)).perform(click());
        onView(withText("Email Sent")).
                inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView()))).
                check(ViewAssertions.matches(isDisplayed()));
    }


}
