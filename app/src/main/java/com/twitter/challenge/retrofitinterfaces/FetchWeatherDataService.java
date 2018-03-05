package com.twitter.challenge.retrofitinterfaces;

import com.twitter.challenge.models.WeatherDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sudavid on 3/3/18.
 */

public interface FetchWeatherDataService {

    @GET("{day}")
    Call<WeatherDataModel> fetchWeatherForDay(@Path("day") String day);
}
