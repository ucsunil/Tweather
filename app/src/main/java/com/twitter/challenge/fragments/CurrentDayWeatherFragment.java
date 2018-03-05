package com.twitter.challenge.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.twitter.challenge.R;
import com.twitter.challenge.customviews.CloudView;
import com.twitter.challenge.customviews.SunView;
import com.twitter.challenge.models.WeatherDataModel;
import com.twitter.challenge.retrofitinterfaces.FetchWeatherDataService;
import com.twitter.challenge.utils.TemperatureUtils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sudavid on 3/3/18.
 */

public class CurrentDayWeatherFragment extends Fragment {

    private static final String TAG = "CurrentWeatherFragment";

    private static final String IS_DATA_LOADED = "isDataLoaded";
    private static final String DATA = "data";

    private static final int CLOUDINESS_THRESHOLD = 50;

    private boolean isDataLoaded = false;
    private LinearLayout weatherView;

    private TextView city;
    private TextView temperature;
    private TextView pressure_humidity;
    private TextView wind;
    private TextView cloudiness;
    private TextView rain;

    private WeatherDataModel weatherData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_weather_view, container, false);
        city = (TextView) view.findViewById(R.id.city);
        temperature = (TextView) view.findViewById(R.id.temperature);
        pressure_humidity = (TextView) view.findViewById(R.id.pressure_humidity);
        wind = (TextView) view.findViewById(R.id.wind);
        cloudiness = (TextView) view.findViewById(R.id.cloudiness);
        rain = (TextView) view.findViewById(R.id.rain);

        weatherView = (LinearLayout) view.findViewById(R.id.weather_view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState == null || !savedInstanceState.getBoolean(IS_DATA_LOADED)) {
            new FetchCurrentWeatherAsyncTask().execute();
        } else {
            Log.d(TAG, "Retrieving current weather information from bundle");
            String json = savedInstanceState.getString(DATA);
            weatherData = new Gson().fromJson(json, WeatherDataModel.class);
            setElementsForWeatherData(weatherData);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(isDataLoaded) {
            Log.d(TAG, "Saving current weather information to bundle");
            outState.putBoolean(IS_DATA_LOADED, isDataLoaded);
            outState.putString(DATA, new Gson().toJson(weatherData));
        }
    }

    private class FetchCurrentWeatherAsyncTask extends AsyncTask<Void, Void, WeatherDataModel> {

        private static final String TAG = "FetchCurrentWeatherTask";

        private static final String ENDPOINT_BASE_URL = "http://twitter-code-challenge.s3-website-us-east-1.amazonaws.com/";
        private static final String CURRENT_WEATHER_ENDPOINT = "current.json";

        @Override
        protected WeatherDataModel doInBackground(Void... voids) {
            Log.d(TAG, "Fetching weather for current day from server");
            Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPOINT_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            FetchWeatherDataService fetchWeatherDataService = retrofit.create(FetchWeatherDataService.class);
            Call<WeatherDataModel> call = fetchWeatherDataService.fetchWeatherForDay(CURRENT_WEATHER_ENDPOINT);
            WeatherDataModel weatherDataModel = null;
            try {
                weatherDataModel = call.execute().body();
            } catch (Exception ex) {

            }
            isDataLoaded = true;
            weatherData = weatherDataModel;
            return weatherDataModel;
        }

        @Override
        public void onPostExecute(WeatherDataModel weatherData) {
            setElementsForWeatherData(weatherData);
        }
    }

    public void setElementsForWeatherData(WeatherDataModel weatherData) {
        city.setText(getString(R.string.city, weatherData.getCity()));
        ViewGroup.LayoutParams params = weatherView.getLayoutParams();
        if(weatherData.getClouds().getCloudiness() > CLOUDINESS_THRESHOLD) {
            CloudView cloudView = new CloudView(getActivity(), false, true, Color.GRAY, Color.TRANSPARENT);
            cloudView.setLayoutParams(params);
            weatherView.addView(cloudView);
        } else {
            SunView sunView = new SunView(getActivity(), false, true, Color.GRAY, Color.TRANSPARENT);
            sunView.setLayoutParams(params);
            weatherView.addView(sunView);
        }
        double temp = weatherData.getWeather().getTemperature();
        temperature.setText(getString(R.string.day_temperature, temp, TemperatureUtils.celsiusToFahrenheit((float)temp)));
        pressure_humidity.setText(getString(R.string.day_pressure_humidity, weatherData.getWeather().getPressure(), weatherData.getWeather().getHumidity()));
        wind.setText(getString(R.string.day_wind_details, weatherData.getWind().getSpeed(), weatherData.getWind().getDegrees()));
        cloudiness.setText(getString(R.string.cloudiness, weatherData.getClouds().getCloudiness()));
        rain.setText(getString(R.string.rain_3h, weatherData.getRain().getDetail()));
    }
}
