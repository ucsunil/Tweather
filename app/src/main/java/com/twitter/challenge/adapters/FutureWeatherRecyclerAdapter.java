package com.twitter.challenge.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twitter.challenge.R;
import com.twitter.challenge.customviews.CloudView;
import com.twitter.challenge.customviews.SunView;
import com.twitter.challenge.models.WeatherDataModel;
import com.twitter.challenge.utils.TemperatureUtils;

import java.util.List;

/**
 * Created by sudavid on 3/3/18.
 */

public class FutureWeatherRecyclerAdapter extends RecyclerView.Adapter<FutureWeatherRecyclerAdapter.WeatherCardViewHolder> {

    private List<WeatherDataModel> weatherDataModels;
    private Context context;

    public FutureWeatherRecyclerAdapter(Context context, List<WeatherDataModel> weatherDataModels) {
        this.context = context;
        this.weatherDataModels = weatherDataModels;
    }


    @Override
    public WeatherCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.future_weather_card, parent,false);
        WeatherCardViewHolder weatherCard = new WeatherCardViewHolder(view, context);
        return weatherCard;
    }

    @Override
    public void onBindViewHolder(WeatherCardViewHolder holder, int position) {
        WeatherDataModel weatherDataModel = weatherDataModels.get(position);
        holder.setDetails(weatherDataModel);
    }

    @Override
    public int getItemCount() {
        return weatherDataModels.size();
    }

    public static class WeatherCardViewHolder extends RecyclerView.ViewHolder {

        private static final int CLOUDINESS_THRESHOLD = 50;

        private TextView city;
        private TextView temperature;
        private TextView pressure_humidity;
        private TextView wind;
        private TextView cloudiness;
        private TextView rain;

        private LinearLayout weatherView;

        private Context context;

        public WeatherCardViewHolder(View view, Context context) {
            super(view);

            city = (TextView) view.findViewById(R.id.city);
            temperature = (TextView) view.findViewById(R.id.temperature);
            pressure_humidity = (TextView) view.findViewById(R.id.pressure_humidity);
            wind = (TextView) view.findViewById(R.id.wind);
            cloudiness = (TextView) view.findViewById(R.id.cloudiness);
            rain = (TextView) view.findViewById(R.id.rain);
            weatherView = (LinearLayout) view.findViewById(R.id.weather_view);

            this.context = context;
        }

        public void setDetails(WeatherDataModel weatherData) {
            city.setText(context.getString(R.string.city, weatherData.getCity()));
            ViewGroup.LayoutParams params = weatherView.getLayoutParams();
            if(weatherData.getClouds().getCloudiness() > CLOUDINESS_THRESHOLD) {
                CloudView cloudView = new CloudView(context, false, true, Color.GRAY, Color.TRANSPARENT);
                cloudView.setLayoutParams(params);
                // The following block is to defend against the RecyclerView from loading multiple
                // SkyViews into the same container. It does not happen if the load process is slow
                // (like when I was debugging)
                if(weatherView.getChildCount() < 1) {
                    weatherView.addView(cloudView);
                }
            } else {
                SunView sunView = new SunView(context, false, true, Color.YELLOW, Color.TRANSPARENT);
                sunView.setLayoutParams(params);
                if(weatherView.getChildCount() < 1) {
                    weatherView.addView(sunView);
                }
            }
            double temp = weatherData.getWeather().getTemperature();
            temperature.setText(context.getString(R.string.day_temperature, temp, TemperatureUtils.celsiusToFahrenheit((float)temp)));
            pressure_humidity.setText(context.getString(R.string.day_pressure_humidity, weatherData.getWeather().getPressure(), weatherData.getWeather().getHumidity()));
            wind.setText(context.getString(R.string.day_wind_details, weatherData.getWind().getSpeed(), weatherData.getWind().getDegrees()));
            cloudiness.setText(context.getString(R.string.cloudiness, weatherData.getClouds().getCloudiness()));
            rain.setText(context.getString(R.string.rain_3h, weatherData.getRain().getDetail()));
        }
    }
}
