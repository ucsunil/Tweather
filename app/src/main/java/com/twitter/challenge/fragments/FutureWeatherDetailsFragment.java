package com.twitter.challenge.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twitter.challenge.R;
import com.twitter.challenge.adapters.FutureWeatherRecyclerAdapter;
import com.twitter.challenge.models.WeatherDataModel;
import com.twitter.challenge.retrofitinterfaces.FetchWeatherDataService;
import com.twitter.challenge.utils.TemperatureUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sudavid on 3/3/18.
 */

public class FutureWeatherDetailsFragment extends Fragment {

    private static final String TAG = "FutureWeather";

    private RecyclerView recycler;
    private TextView stdDeviation;
    private FutureWeatherRecyclerAdapter adapter;
    private List<WeatherDataModel> weatherDataModels;

    private boolean isDataLoaded = false;

    private static final String IS_DATA_LOADED = "isDataLoaded";
    private static final String STD_DEVIATION = "standard_deviation";
    private static final String DATA = "data";

    private double std_deviation;

    private FetchFutureWeatherDetails asyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherDataModels = new ArrayList<>();
        setRetainInstance(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.future_weather_recycler_view, viewGroup, false);
        stdDeviation = (TextView) view.findViewById(R.id.standard_deviation);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        adapter = new FutureWeatherRecyclerAdapter(getActivity(), weatherDataModels);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null || !savedInstanceState.getBoolean(IS_DATA_LOADED)) {
            Log.d(TAG, "Making a call to server to obtain weather information");
            String[] futureWeatherEndpoints = {"future_1.json", "future_2.json", "future_3.json", "future_4.json", "future_5.json"};
            asyncTask = new FetchFutureWeatherDetails();
            asyncTask.execute(futureWeatherEndpoints);
        } else {
            Log.d(TAG, "Retrieving weather information from bundle");
            String json = savedInstanceState.getString(DATA);
            Type listType = new TypeToken<ArrayList<WeatherDataModel>>(){}.getType();
            weatherDataModels = new Gson().fromJson(json, listType);
            adapter.notifyDataSetChanged();
            stdDeviation.setText(getString(R.string.std_dev, savedInstanceState.getDouble(STD_DEVIATION)));
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(isDataLoaded) {
            Log.d(TAG, "Saving weather information to bundle");
            outState.putBoolean(IS_DATA_LOADED, isDataLoaded);
            outState.putString(DATA, new Gson().toJson(weatherDataModels));
            outState.putDouble(STD_DEVIATION, std_deviation);
        }
    }

    public class FetchFutureWeatherDetails extends AsyncTask<String, WeatherDataModel, List<WeatherDataModel>> {

        private static final String TAG = "FetchFutureDetails";

        private static final String ENDPOINT_BASE_URL = "http://twitter-code-challenge.s3-website-us-east-1.amazonaws.com/";

        @Override
        protected List<WeatherDataModel> doInBackground(String... endpoints) {
            for(int i = 0; i < endpoints.length; i++) {
                Log.d(TAG, "Fetching weather from: " + endpoints[i]);
                Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPOINT_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                FetchWeatherDataService fetchWeatherDataService = retrofit.create(FetchWeatherDataService.class);
                Call<WeatherDataModel> call = fetchWeatherDataService.fetchWeatherForDay(endpoints[i]);
                WeatherDataModel weatherDataModel = null;
                try {
                    weatherDataModel = call.execute().body();
                    publishProgress(weatherDataModel);
                } catch (Exception ex) {

                }
            }
            isDataLoaded = true;
            return weatherDataModels;
        }

        @Override
        protected void onProgressUpdate(WeatherDataModel... models) {
            weatherDataModels.add(models[models.length-1]);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(List<WeatherDataModel> weatherDataModels) {
            std_deviation = TemperatureUtils.calculateStandardDeviationForTemperatures(weatherDataModels);
            stdDeviation.setText(getString(R.string.std_dev, std_deviation));
        }
    }
}
