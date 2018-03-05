package com.twitter.challenge.fragments;

import android.app.FragmentManager;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.LinearLayout;

import com.twitter.challenge.MainActivity;
import com.twitter.challenge.R;
import com.twitter.challenge.customviews.CloudView;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by sudavid on 3/4/18.
 */

@RunWith(AndroidJUnit4.class)
public class CurrentWeatherDetailsFragmentTest {

    private static final String TAG_CURRENT_DETAILS_FRAGMENT = "currentDetails";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    @MediumTest
    public void testWeatherViewBeingShown() throws InterruptedException {
        MainActivity activity = activityTestRule.getActivity();
        FragmentManager fm = activity.getFragmentManager();
        CurrentDayWeatherFragment currentDayWeatherFragment = (CurrentDayWeatherFragment) fm.findFragmentByTag(TAG_CURRENT_DETAILS_FRAGMENT);
        LinearLayout weather_view = (LinearLayout) activity.findViewById(R.id.weather_view);
        Thread.sleep(500);
        View view = weather_view.getChildAt(0);
        // We can verify this since we are sending the mock data and we know we are setting a cloud view
        if(!(view instanceof CloudView)) {
            throw new AssertionFailedError("Found unexpected view with tyoe: " + view.getClass().getName());
        }
    }
}
