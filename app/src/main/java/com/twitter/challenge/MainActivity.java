package com.twitter.challenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.twitter.challenge.fragments.CurrentDayWeatherFragment;
import com.twitter.challenge.fragments.FutureWeatherDetailsFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private static final String TAG_FUTURE_DETAILS_FRAGMENT = "futureDetails";
    private static final String TAG_CURRENT_DETAILS_FRAGMENT = "currentDetails";
    private static final String CURRENT_FRAGMENT = "currentFragment";

    private Button currentWeather;
    private Button futureWeather;

    private FrameLayout container;
    private CurrentDayWeatherFragment currentDayWeatherFragment;
    private FutureWeatherDetailsFragment futureWeatherDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentWeather = (Button) findViewById(R.id.show_current_weather);
        currentWeather.setOnClickListener(this);
        futureWeather = (Button) findViewById(R.id.show_future_weather);
        futureWeather.setOnClickListener(this);
        container = (FrameLayout) findViewById(R.id.container);

        if(savedInstanceState == null) {
            currentDayWeatherFragment = new CurrentDayWeatherFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, currentDayWeatherFragment, TAG_CURRENT_DETAILS_FRAGMENT).commit();
            currentWeather.setEnabled(false);
        } else {
            currentDayWeatherFragment = (CurrentDayWeatherFragment) getFragmentManager().findFragmentByTag(TAG_CURRENT_DETAILS_FRAGMENT);
            futureWeatherDetailsFragment = (FutureWeatherDetailsFragment) getFragmentManager().findFragmentByTag(TAG_FUTURE_DETAILS_FRAGMENT);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null && savedInstanceState.getString(CURRENT_FRAGMENT) != null) {
            if(savedInstanceState.getString(CURRENT_FRAGMENT).equals(TAG_CURRENT_DETAILS_FRAGMENT)) {
                // Means current weather detail are being shown; gray out the button for this
                currentWeather.setEnabled(false);
            } else {
                futureWeather.setEnabled(false);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);

        if(currentDayWeatherFragment != null && currentDayWeatherFragment.isVisible()) {
            saveInstanceState.putString(CURRENT_FRAGMENT, TAG_CURRENT_DETAILS_FRAGMENT);
        } else {
            saveInstanceState.putString(CURRENT_FRAGMENT, TAG_FUTURE_DETAILS_FRAGMENT);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_current_weather:
                if(currentDayWeatherFragment == null) {
                    currentDayWeatherFragment = new CurrentDayWeatherFragment();
                    getFragmentManager().beginTransaction().replace(R.id.container, currentDayWeatherFragment, TAG_CURRENT_DETAILS_FRAGMENT).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, currentDayWeatherFragment).commit();
                }
                view.setEnabled(false);
                futureWeather.setEnabled(true);
                break;
            case R.id.show_future_weather:
                if(futureWeatherDetailsFragment == null) {
                    futureWeatherDetailsFragment = new FutureWeatherDetailsFragment();
                    getFragmentManager().beginTransaction().replace(R.id.container, futureWeatherDetailsFragment, TAG_FUTURE_DETAILS_FRAGMENT).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, futureWeatherDetailsFragment).commit();
                }
                view.setEnabled(false);
                currentWeather.setEnabled(true);
                break;
        }
    }
}
