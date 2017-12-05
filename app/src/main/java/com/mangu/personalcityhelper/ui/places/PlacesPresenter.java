package com.mangu.personalcityhelper.ui.places;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.data.local.Place;
import com.mangu.personalcityhelper.ui.base.BasePresenter;
import com.mangu.personalcityhelper.util.PlacesUtil;
import com.mangu.personalcityhelper.util.scheduler.SchedulerUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.mangu.personalcityhelper.util.PlacesUtil.addMarker;

public class PlacesPresenter extends BasePresenter<PlacesMvpView> {
    private final DataManager mDataManager;


    @Inject
    public PlacesPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(PlacesMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void moveTo() {

    }

    LocationListener getLocationListener() {
        return new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
    }

    void getSurroundingPlaces(LatLng latLng) {
        checkViewAttached();
        getMvpView().showProgress(true);
        String latWithLng = Double.toString(latLng.latitude) + "," +
                Double.toString(latLng.longitude);
        Observable<JsonObject> mRestaurants = mDataManager.getRestaurants(latWithLng);
        Observable<JsonObject> mBar = mDataManager.getBars(latWithLng);

        Observable.zip(mRestaurants, mBar, PlacesUtil::joinPlaces).
                compose(SchedulerUtils.ioToMain()).subscribe(this::processResult);

        getMvpView().showProgress(false);
    }


    private void processResult(List<Place> places) {
        for (Place place : places) {
            getMvpView().getMap().
                    addMarker(new MarkerOptions().
                            title(place.getmName()).
                            snippet(place.getmSnippet()).
                            position(new LatLng(place.getmLatitude(), place.getmLongitude())));
        }
        getMvpView().setListPlaces(places);
    }

    private void processResult(JsonObject result) {
        Log.i("PlacesPresenter", result.toString());
        List<Marker> markerList = new ArrayList<>();
        GoogleMap map = getMvpView().getMap();
        for (JsonElement place : result.getAsJsonArray("results")) {
            JsonObject geometry = place.getAsJsonObject().getAsJsonObject("geometry");
            String name = place.getAsJsonObject().get("name").getAsString();
            String vicinity = place.getAsJsonObject().get("vicinity").getAsString();
            Marker marker = map.addMarker(
                    new MarkerOptions().position(new LatLng(
                            geometry.getAsJsonObject("location").get("lat").getAsDouble(),
                            geometry.getAsJsonObject("location").get("lng").getAsDouble())).
                            title(name).snippet(vicinity));
            markerList.add(marker);
        }
        getMvpView().mergeList(markerList);
    }

    void filterOptions(List<Place> mPlaces, int position) {
        GoogleMap map = getMvpView().getMap();
        map.clear();
        if (position == PlacesActivity.mFinalPositionFilter) {
            for (Place place : mPlaces) {
                map.addMarker(addMarker(place));
            }
        } else {
            for (Place place : mPlaces) {
                if (place.getmKind() == position) {
                    map.addMarker(addMarker(place));
                }
            }
        }
    }
}
