package com.mangu.personalcityhelper.data.local;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class WeatherCard {
    String mTemperature;
    String mPressure;
    String mMinAndMax;
    int mPhotoId;

    public WeatherCard(String mTemperature, String mPressure, String mMinAndMax, int mPhotoId) {
        this.mTemperature = mTemperature;
        this.mPressure = mPressure;
        this.mMinAndMax = mMinAndMax;
        this.mPhotoId = mPhotoId;
    }
}
