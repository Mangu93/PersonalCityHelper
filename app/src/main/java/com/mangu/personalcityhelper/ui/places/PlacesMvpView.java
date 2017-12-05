package com.mangu.personalcityhelper.ui.places;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mangu.personalcityhelper.data.local.Place;
import com.mangu.personalcityhelper.ui.base.MvpView;

import java.util.List;

public interface PlacesMvpView extends MvpView {

    void showProgress(boolean show);

    void showErrorSnackMessage(Throwable e);

    GoogleMap getMap();

    void mergeList(List<Marker> markerList);

    void setListPlaces(List<Place> places);
}
