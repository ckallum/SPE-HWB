package com.example.healthproject.Global;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

import androidx.test.filters.LargeTest;
import androidx.test.filters.RequiresDevice;
import androidx.test.filters.SdkSuppress;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import com.example.healthproject.Activity.LoginActivity;

import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class SDKTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);
    @Test
    @RequiresDevice
    public void testRequiresDevice() {
        Log.d("Test Filters", "This test requires a device");
        Activity activity = activityTestRule.getActivity();
        assertNotNull("MainActivity is not available", activity);
    }

    @Test
    @SdkSuppress(minSdkVersion = 20)
    public void testMinSdkVersion() {
        Log.d("Test Filters", "Checking for min sdk >= 20");
        Activity activity = activityTestRule.getActivity();
        assertNotNull("MainActivity is not available", activity);
    }

    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.KITKAT_WATCH)
    public void testMinBuild() {
        Log.d("Test Filters", "Checking for min build > Lollipop");
        Activity activity = activityTestRule.getActivity();
        assertNotNull("MainActivity is not available", activity);
    }

    @Test
    @SmallTest
    public void testSmallTest() {
        Log.d("Test Filters", "this is a small test");
        Activity activity = activityTestRule.getActivity();
        assertNotNull("MainActivity is not available", activity);
    }

    @Test
    @LargeTest
    public void testLargeTest() {
        Log.d("Test Filters", "This is a large test");
        Activity activity = activityTestRule.getActivity();
        assertNotNull("MainActivity is not available", activity);
    }
}
