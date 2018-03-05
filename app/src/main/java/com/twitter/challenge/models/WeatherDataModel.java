package com.twitter.challenge.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sudavid on 3/3/18.
 */

public class WeatherDataModel {

    @SerializedName("name")
    private String city;

    private Coordinates coord;
    private Weather weather;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    private static class Coordinates {
        @SerializedName("lon")
        private double longitude;
        @SerializedName("lat")
        private double latitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

    public static class Weather {
        @SerializedName("temp")
        private double temperature;
        private int pressure;
        private int humidity;

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }
    }

    public static class Wind {
        private double speed;
        @SerializedName("deg")
        private int degrees;

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public int getDegrees() {
            return degrees;
        }

        public void setDegrees(int degrees) {
            this.degrees = degrees;
        }
    }

    public static class Rain {
        @SerializedName("3h")
        private int detail;

        public int getDetail() {
            return detail;
        }

        public void setDetail(int detail) {
            this.detail = detail;
        }
    }

    public static class Clouds {
        private int cloudiness;

        public int getCloudiness() {
            return cloudiness;
        }

        public void setCloudiness(int cloudiness) {
            this.cloudiness = cloudiness;
        }
    }
}
