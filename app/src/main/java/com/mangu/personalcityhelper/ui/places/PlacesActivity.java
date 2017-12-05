package com.mangu.personalcityhelper.ui.places;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mangu.personalcityhelper.BuildConfig;
import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.data.local.Place;
import com.mangu.personalcityhelper.ui.base.BaseActivity;
import com.mangu.personalcityhelper.ui.common.ErrorView;
import com.mangu.personalcityhelper.util.NetworkUtil;
import com.mangu.personalcityhelper.util.ViewUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.google.android.gms.location.places.Places.GEO_DATA_API;
import static com.google.android.gms.location.places.Places.PLACE_DETECTION_API;
import static com.mangu.personalcityhelper.util.ViewUtil.createErrorSnackbar;

public class PlacesActivity extends BaseActivity implements OnMapReadyCallback, PlacesMvpView,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleMap.OnMarkerClickListener, RadioGroup.OnCheckedChangeListener {

    private static final int MY_PERMISSIONS_ACCESS_LOCATION = 14;
    private GoogleMap mMap;
    @Inject
    PlacesPresenter mPlacesPresenter;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.map)
    FrameLayout mFrameLayout;
    @BindView(R.id.view_error)
    ErrorView mErrorView;
    private GoogleApiClient mGac;
    private LocationManager mLocationManager;
    private List<Marker> mMarkerList;
    private LocationListener mLocationListener;
    private Dialog mDialog;
    private List<Place> mPlaces;
    private List<String> mOptionsFilter;
    public static Integer mFinalPositionFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        mPlacesPresenter.attachView(this);
        mMarkerList = new ArrayList<>();
        initialiteFilters();
        mLocationListener = mPlacesPresenter.getLocationListener();
        mLocationManager = (LocationManager) getApplicationContext().
                getSystemService(LOCATION_SERVICE);
        mGac = new GoogleApiClient.Builder(this)
                .addApi(GEO_DATA_API)
                .addApi(PLACE_DETECTION_API)
                .enableAutoManage(this, this).
                        build();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);

    }

    private void initialiteFilters() {
        mPlaces = new ArrayList<>();
        mOptionsFilter = new ArrayList<>();
        mOptionsFilter.add(0, getString(R.string.restaurant));
        mOptionsFilter.add(1, getString(R.string.bar));
        mOptionsFilter.add(2, getString(R.string.cafe));
        mOptionsFilter.add(3, getString(R.string.takeaway));
        mOptionsFilter.add(getString(R.string.all));
        mFinalPositionFilter = mOptionsFilter.indexOf(getString(R.string.all));
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_ACCESS_LOCATION);


        } else {
            if (BuildConfig.DEBUG) {
                mMap.setMyLocationEnabled(true);
                LatLng latLng = new LatLng(36.71607d, -4.28753491d);
                moveTo(latLng);
            } else {
                mMap.setMyLocationEnabled(true);
                List<String> providers = mLocationManager.getProviders(true);
                Location bestLocation = null;
                for (String provider : providers) {
                    Location locationToCheck = mLocationManager.getLastKnownLocation(provider);
                    if (locationToCheck == null) continue;
                    if (bestLocation == null ||
                            locationToCheck.getAccuracy() > bestLocation.getAccuracy()) {
                        bestLocation = locationToCheck;
                    }
                }
                if (bestLocation == null) {
                    Criteria criteria = new Criteria();
                    criteria.setAccuracy(Criteria.ACCURACY_FINE);
                    criteria.setPowerRequirement(Criteria.POWER_HIGH);
                    String provider = mLocationManager.getBestProvider(criteria, true);
                    if (provider == null) {
                        mLocationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER, 0, 0,
                                mLocationListener, getMainLooper()
                        );
                    } else {
                        mLocationManager.requestLocationUpdates(provider, 0, 0,
                                mLocationListener, getMainLooper());
                    }
                    bestLocation = mLocationManager.
                            getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                if (bestLocation != null) {
                    LatLng latLng = new LatLng(bestLocation.getLatitude(),
                            bestLocation.getLongitude());
                    moveTo(latLng);
                } else {
                    //If there are no available locations, put default on the city hall
                    LatLng latLng = new LatLng(36.71607d, -4.28753491d);
                    moveTo(latLng);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_LOCATION:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {

                    getLocation();
                } else {
                    showErrorSnackMessage(new SecurityException());
                }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGac != null) {
            mGac.connect();
        }
    }

    @Override
    protected void onStop() {
        if (mGac != null && mGac.isConnected()) {
            mGac.disconnect();
        }
        super.onStop();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_places;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        if (NetworkUtil.isNetworkConnected(this)) {
            getLocation();
            mPlacesPresenter.getSurroundingPlaces(mMarkerList.get(0).getPosition());
        } else {
            mErrorView.addView(ViewUtil.generateErrorLayout(this).get(0));
            mErrorView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showProgress(boolean show) {
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);

    }

    @Override
    public void showErrorSnackMessage(Throwable e) {
        Timber.e(e);
        if (e instanceof IOException) {
            createErrorSnackbar(this.mFrameLayout).show();
        }
    }

    @Override
    public GoogleMap getMap() {
        return mMap;
    }

    @Override
    public void mergeList(List<Marker> markerList) {
        this.mMarkerList.addAll(markerList);
    }

    @Override
    public void setListPlaces(List<Place> places) {
        this.mPlaces.addAll(places);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(35), 2000, null);
        marker.showInfoWindow();
        return false;
    }

    public void moveTo(Marker marker) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        marker.showInfoWindow();
    }

    public void moveTo(LatLng latLng) {
        Marker marker = mMap.addMarker(
                new MarkerOptions().position(latLng).title(getString(R.string.here)).
                        snippet(getString(R.string.last_known_location))
        );
        moveTo(marker);
        mMarkerList.add(0, marker);
    }

    @OnClick({R.id.btn_filter})
    public void filter(View view) {
        mDialog = new Dialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.filter_options);
        List<String> listOptions = new ArrayList<>();
        listOptions.add(getString(R.string.restaurant));
        listOptions.add(getString(R.string.bar));
        listOptions.add(getString(R.string.cafe));
        listOptions.add(getString(R.string.takeaway));
        listOptions.add(getString(R.string.all));
        RadioGroup radioGroup = (RadioGroup) mDialog.findViewById(R.id.radio_group);
        for (String option : listOptions) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            radioGroup.addView(radioButton);
        }
        radioGroup.setOnCheckedChangeListener(this);
        mDialog.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton btn = null;
        for (int index = 0; index < radioGroup.getChildCount(); index++) {
            if (radioGroup.getChildAt(index).getId() == i) {
                btn = (RadioButton) radioGroup.getChildAt(index);
                break;
            }
        }
        if (btn != null) {
            String option = btn.getText().toString();
            int position = -1;
            if (mOptionsFilter.contains(option)) {
                position = mOptionsFilter.indexOf(option);
            }
            mPlacesPresenter.filterOptions(mPlaces, position);
            mDialog.dismiss();
        }
    }
}
