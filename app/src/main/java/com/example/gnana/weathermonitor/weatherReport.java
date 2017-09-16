package com.example.gnana.weathermonitor;

/**
 * Created by gnana on 29-04-2017.
 */

public class weatherReport {

    private String temp;
    private String date;
    private String humidity;
    private String lightIntensity;
    private String rain;
    private String dewPoint;

    public weatherReport(String temp, String date, String humidity, String lightIntensity, String rain) {
        this.temp = temp;
        this.date = date;
        this.humidity = humidity;
        this.lightIntensity = lightIntensity;
        this.rain = rain;
    }

    public weatherReport(String temp, String date, String humidity, String lightIntensity, String rain, String dewPoint) {
        this.temp = temp;
        this.date = date;
        this.humidity = humidity;
        this.lightIntensity = lightIntensity;
        this.rain = rain;
        this.dewPoint = dewPoint;
    }

    public String getTemp() {
        return temp;
    }

    public String getDate() {
        return date;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getLightIntensity() {
        return lightIntensity;
    }

    public String getRain() {
        return rain;
    }

    public String getDewPoint() {
        return dewPoint;
    }
}
