package com.mangu.personalcityhelper.data.remote;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.mangu.personalcityhelper.BuildConfig.OpenWeatherApiToken;

@SuppressWarnings("SameParameterValue")
public interface WeatherService {

    @Headers({"appid:" + OpenWeatherApiToken})
    @GET("weather")
    Observable<JsonObject> getWeatherForToday(@Query("id") Integer id);

    @Headers({"appid:" + OpenWeatherApiToken})
    @GET("forecast")
    Observable<JsonObject> getForecast(@Query("id") Integer id);
}
