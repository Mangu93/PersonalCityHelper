package com.mangu.personalcityhelper.data.local;


public class Place {
    private double mLatitude;
    private double mLongitude;
    private String mName;
    private String mSnippet;
    private Integer mKind;

    public Place(double mLatitude, double mLongitude, String mName, String mSnippet,
                 Integer mKind) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mName = mName;
        this.mSnippet = mSnippet;
        this.mKind = mKind;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSnippet() {
        return mSnippet;
    }

    public void setmSnippet(String mSnippet) {
        this.mSnippet = mSnippet;
    }

    public Integer getmKind() {
        return mKind;
    }

    public void setmKind(Integer mKind) {
        this.mKind = mKind;
    }
}
