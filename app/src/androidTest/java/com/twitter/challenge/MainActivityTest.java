package com.twitter.challenge;

import android.content.pm.ActivityInfo;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by sudavid on 3/4/18.
 */

/**
 * Before running this test, ensure that animations for the weather views are turned off. When creating,
 * the cloudy or sunny view, the isStatic flag needs to be set to true and the animated flag needs to be
 * set to false.
 *
 * TO DO **
 * Set up a mock HTTP server to service the requests
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void verifyIfInitialUIDiaLoadCorrectly() {
        onView(withId(R.id.container)).check(matches(ViewMatchers.isCompletelyDisplayed()));
        onView(withId(R.id.show_current_weather)).check(matches(not(isEnabled())));
        onView(withId(R.id.show_future_weather)).check(matches(isEnabled()));
        onView(withId(R.id.temperature)).check(matches(ViewMatchers.isCompletelyDisplayed()));
        onView(withId(R.id.pressure_humidity)).check(matches(ViewMatchers.isCompletelyDisplayed()));
        onView(withId(R.id.wind)).check(matches(ViewMatchers.isCompletelyDisplayed()));
        onView(withId(R.id.cloudiness)).check(matches(ViewMatchers.isCompletelyDisplayed()));
        onView(withId(R.id.rain)).check(matches(ViewMatchers.isCompletelyDisplayed()));
    }

    @Test
    public void verifyIfInitialUIDiaLoadCorrectlyLandscape() {
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.container)).check(matches(ViewMatchers.isCompletelyDisplayed()));
        onView(withId(R.id.show_current_weather)).check(matches(not(isEnabled())));
        onView(withId(R.id.show_future_weather)).check(matches(isEnabled()));
        onView(withId(R.id.temperature)).check(matches(ViewMatchers.isCompletelyDisplayed()));
        onView(withId(R.id.pressure_humidity)).check(matches(ViewMatchers.isCompletelyDisplayed()));
        onView(withId(R.id.wind)).check(matches(ViewMatchers.isCompletelyDisplayed()));
        onView(withId(R.id.cloudiness)).check(matches(ViewMatchers.isCompletelyDisplayed()));
        onView(withId(R.id.rain)).check(matches(ViewMatchers.isCompletelyDisplayed()));
    }
}
