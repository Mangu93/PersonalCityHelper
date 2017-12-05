package com.mangu.personalcityhelper.data;

import com.google.gson.JsonObject;
import com.mangu.personalcityhelper.data.model.bus.LineList;
import com.mangu.personalcityhelper.data.model.lineschedule.LineSchedule;
import com.mangu.personalcityhelper.data.remote.BusService;
import com.mangu.personalcityhelper.data.remote.PlacesService;
import com.mangu.personalcityhelper.data.remote.WeatherService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class DataManager {
    private final WeatherService mWeatherService;

    private final BusService mBusService;

    private final PlacesService mPlacesService;

    @Inject
    public DataManager(WeatherService weatherService, BusService busService,
                       PlacesService placesService) {
        this.mWeatherService = weatherService;
        this.mBusService = busService;
        this.mPlacesService = placesService;
    }

    public Observable<JsonObject> getWeatherForToday() {
        return mWeatherService.getWeatherForToday(2511852);
    }

    public Observable<JsonObject> getForecast() {
        return mWeatherService.getForecast(2511852);
    }

    public Observable<LineList> getBusLines() {
        return mBusService.getBusLines("4");
    }

    public Observable<LineSchedule> getLineSchedule(String day, String line, String month) {
        return mBusService.getLineSchedule(day, "", "ES", line, month);
    }

    public Observable<JsonObject> getRestaurants(String latLng) {
        return mPlacesService.getPlaces(latLng, "restaurant", "1000");
    }

    public Observable<JsonObject> getBars(String latWithLng) {
        return mPlacesService.getPlaces(latWithLng, "bar", "1000");
    }

    public Observable<JsonObject> getCafe(String latLng) {
        return mPlacesService.getPlaces(latLng, "cafe", "1000");
    }

    public Observable<JsonObject> getTakeaway(String latLng) {
        return mPlacesService.getPlaces(latLng, "meal_takeaway", "1000");
    }
}
