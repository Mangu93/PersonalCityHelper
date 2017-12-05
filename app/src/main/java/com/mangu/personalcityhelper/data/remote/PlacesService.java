package com.mangu.personalcityhelper.data.remote;


import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.mangu.personalcityhelper.BuildConfig.MapsAPIToken;

public interface PlacesService {

    @Headers({"appid:" + MapsAPIToken})
    @GET("nearbysearch/json")
    Observable<JsonObject> getPlaces (@Query("location") String location, @Query("type")
            String type, @Query("radius") String radius);
}
