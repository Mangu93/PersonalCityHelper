package com.mangu.personalcityhelper.data.remote;


import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface PlacesService {

    @Headers({"appid:" + "AIzaSyCYPsYO3wSP-Z_3SFvvUlShSOQbKL-1ENI"})
    @GET("nearbysearch/json")
    Observable<JsonObject> getPlaces (@Query("location") String location, @Query("type")
            String type, @Query("radius") String radius);
}
