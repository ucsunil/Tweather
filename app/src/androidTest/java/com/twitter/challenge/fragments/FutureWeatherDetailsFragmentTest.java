package com.twitter.challenge.fragments;

import android.app.FragmentManager;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.twitter.challenge.MainActivity;
import com.twitter.challenge.R;

import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by sudavid on 3/4/18.
 */

/**
 * To test this fragment using Espresso, you will need to disable animations in the weather views.
 */
public class FutureWeatherDetailsFragmentTest {

    private static final String TAG_FUTURE_DETAILS_FRAGMENT = "futureDetails";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    /**
     * The large sleep timeouts in the following test method will be eliminated once we
     * migrate to using mock servers.
     * @throws InterruptedException
     */
    @Test
    @LargeTest
    public void testFutureWeatherDetailsFragment() throws InterruptedException {
        MainActivity activity = activityTestRule.getActivity();
        FragmentManager fm = activity.getFragmentManager();
        FutureWeatherDetailsFragment futureWeatherDetailsFragment = new FutureWeatherDetailsFragment();
        Thread.sleep(3000);
        fm.beginTransaction().replace(R.id.container, futureWeatherDetailsFragment, TAG_FUTURE_DETAILS_FRAGMENT).commit();
        Thread.sleep(4000);
        TextView stdDeviation = (TextView) activity.findViewById(R.id.standard_deviation);
        assertNotNull(stdDeviation);
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recycler);
        assertNotNull(recyclerView);
    }
}
