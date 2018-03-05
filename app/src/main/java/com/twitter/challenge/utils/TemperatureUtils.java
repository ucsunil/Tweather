package com.twitter.challenge.utils;

import com.twitter.challenge.models.WeatherDataModel;

import java.util.List;

public class TemperatureUtils {
    /**
     * Converts temperature in Celsius to temperature in Fahrenheit.
     *
     * @param temperatureInCelsius Temperature in Celsius to convert.
     * @return Temperature in Fahrenheit.
     */
    public static float celsiusToFahrenheit(float temperatureInCelsius) {
        return temperatureInCelsius * 1.8f + 32;
    }

    public static double calculateStandardDeviationForTemperatures(List<WeatherDataModel> weatherDataModels) {
        double sumTemperatures = 0;
        for(WeatherDataModel weatherDataModel : weatherDataModels) {
            sumTemperatures += weatherDataModel.getWeather().getTemperature();
        }
        double avgTemperature = sumTemperatures / weatherDataModels.size();
        double sumDeviations = 0;
        for(WeatherDataModel weatherDataModel : weatherDataModels) {
            sumDeviations = sumDeviations + Math.pow((weatherDataModel.getWeather().getTemperature() - avgTemperature), 2);
        }
        double variance = sumDeviations/(weatherDataModels.size()-1);
        double stdDeviation = Math.sqrt(variance);
        return stdDeviation;
    }
}
