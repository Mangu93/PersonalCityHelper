package com.mangu.personalcityhelper.data;

import com.google.gson.JsonObject;
import com.mangu.personalcityhelper.data.remote.WeatherService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class DataManager {
    private final WeatherService mWeatherService;

    @Inject
    public DataManager(WeatherService weatherService) {
        this.mWeatherService = weatherService;
    }

    public Observable<JsonObject> getWeatherForToday() {
        return mWeatherService.getWeatherForToday(2511852);
    }

    public Observable<JsonObject> getForecast() {
        return mWeatherService.getForecast(2511852);
    }
}
