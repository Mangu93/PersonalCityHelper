package com.mangu.personalcityhelper.util;


import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mangu.personalcityhelper.data.local.Place;

import java.util.ArrayList;
import java.util.List;

public class PlacesUtil {
    public static List<Place> joinPlaces(JsonObject places1, JsonObject places2) throws Exception {
        List<Place> placeList = new ArrayList<>();
        JsonArray ar = places1.getAsJsonArray("results");
        ar.addAll(places2.getAsJsonArray("results"));
        for (JsonElement place : ar) {
            JsonObject geometry = place.getAsJsonObject().getAsJsonObject("geometry");
            String name = place.getAsJsonObject().get("name").getAsString();
            String vicinity = place.getAsJsonObject().get("vicinity").getAsString();
            Double latitude = geometry.getAsJsonObject("location").get("lat").getAsDouble();
            Double longitude = geometry.getAsJsonObject("location").get("lng").getAsDouble();
            String type = place.getAsJsonObject().getAsJsonArray("types").
                    get(0).toString();
            placeList.add(new Place(latitude, longitude, name, vicinity, getTypeOfPlace(type)));
        }
        return placeList;
    }

    /**
     * TODO Input here the type of places you use in the API request.
     */
    @NonNull
    private static Integer getTypeOfPlace(String type) {
        switch (type.replace("\"", "")) {
            case "restaurant":
                return 0;
            case "bar":
                return 1;
            case "cafe":
                return 2;
            case "meal_delivery":
                return 3;
            default:
                return -1;
        }
    }

    @NonNull
    public static MarkerOptions addMarker(Place place) {
        return new MarkerOptions().
                title(place.getmName()).
                snippet(place.getmSnippet()).
                position(new LatLng(place.getmLatitude(), place.getmLongitude()));
    }

}
