package com.mangu.personalcityhelper.data.model;

import java.util.Locale;

public class BeachPrediction {
    private String mDate;
    private String mTermicSensation;
    private String mWindMorning;
    private String mWindAfternoon;
    private String mSurfMorning;
    private String mSurfAfternoon;
    private int mMaximumUv;
    private int mWaterTemperature;
    private int mMaximumTemperature;
    private String mDay;
    private String mWater;
    private String mMaxim;
    private String mSurf;
    private String mWind;
    private String mSensation;
    private String mUvMax;
    private String mSky;
    private String mMorning;
    private String mAfternoon;

    public BeachPrediction() {

    }

    public BeachPrediction(String mDay, String mWater, String mMaxim, String mSurf,
                           String mWind, String mSensation, String mUvMax, String mSky,
                           String mMorning, String mAfternoon) {
        this.mDay = mDay;
        this.mWater = mWater;
        this.mMaxim = mMaxim;
        this.mSurf = mSurf;
        this.mWind = mWind;
        this.mSensation = mSensation;
        this.mUvMax = mUvMax;
        this.mSky = mSky;
        this.mMorning = mMorning;
        this.mAfternoon = mAfternoon;
    }

    public String presentation() {
        return mDay + " : " + getmDate() + ".<br>"
                + mMaxim + ": " + getmMaximumTemperature() + "º" + ".<br>"
                + mWater + ": " + getmWaterTemperature() + "º" + ".<br>"
                + mSensation + ": " + getmTermicSensation() + ".<br>"
                + mUvMax + ": " + getmMaximumUv() + ".<br>"
                + mSurf + ": " + mMorning + " -> " + getmSurfMorning() + " , " + mAfternoon
                + " -> " + getmSurfAfternoon() + ".<br>"
                + mWind + ": " + mMorning + " -> " + getmWindMorning() + ", " + mAfternoon + " "
                + " -> " + getmWindAfternoon() + ".<br>";
    }

    private String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        String year = mDate.substring(0, 4);
        String month = mDate.substring(4, 6);
        String day = mDate.substring(6);
        this.mDate = day + "-" + month + "-" + year;
    }

    private String getmSurfMorning() {

        return mSurfMorning;
    }

    public void setmSurfMorning(String mSurfMorning) {
        if (mSurfMorning.contains("bil")) {
            if (Locale.getDefault().getDisplayName().contains("English")) {
                this.mSurfMorning = "Weak";
            } else {
                this.mSurfMorning = "Debil";
            }
        } else {
            if (Locale.getDefault().getDisplayName().contains("English")) {
                this.mSurfMorning = "Strong";
            } else {
                this.mSurfMorning = "Fuerte";
            }
        }
    }

    private String getmSurfAfternoon() {
        if (mSurfAfternoon == null) return "No info";
        return mSurfAfternoon;
    }

    public void setmSurfAfternoon(String mSurfAfternoon) {
        if (mSurfAfternoon.contains("bil")) {
            if (Locale.getDefault().getDisplayName().contains("English")) {
                this.mSurfAfternoon = "Weak";
            } else {
                this.mSurfAfternoon = "Debil";
            }
        } else {
            if (Locale.getDefault().getDisplayName().contains("English")) {
                this.mSurfAfternoon = "Strong";
            } else {
                this.mSurfAfternoon = "Fuerte";
            }
        }
    }

    private String getmTermicSensation() {
        return mTermicSensation;
    }

    public void setmTermicSensation(String mTermicSensation) {
        if (mTermicSensation.equalsIgnoreCase("calor moderado")) {
            if (Locale.getDefault().getDisplayName().contains("English")) {
                this.mTermicSensation = "Warmth";
            } else {
                this.mTermicSensation = mTermicSensation;
            }
        } else if (mTermicSensation.equalsIgnoreCase("calor agradable")) {
            if (Locale.getDefault().getDisplayName().contains("English")) {
                this.mTermicSensation = "Pleasant warmth";
            } else {
                this.mTermicSensation = mTermicSensation;
            }
        } else {
            this.mTermicSensation = mTermicSensation;
        }

    }

    private int getmWaterTemperature() {
        return mWaterTemperature;
    }

    public void setmWaterTemperature(int mWaterTemperature) {
        this.mWaterTemperature = mWaterTemperature;
    }

    private int getmMaximumUv() {
        return mMaximumUv;
    }

    public void setmMaximumUv(int mMaximumUv) {
        this.mMaximumUv = mMaximumUv;
    }

    private int getmMaximumTemperature() {
        return mMaximumTemperature;
    }

    public void setmMaximumTemperature(int mMaximumTemperature) {
        this.mMaximumTemperature = mMaximumTemperature;
    }

    private String getmWindMorning() {
        return mWindMorning;
    }

    public void setmWindMorning(String mWindMorning) {
        if (Locale.getDefault().getDisplayName().contains("English")) {
            if (mWindMorning.equalsIgnoreCase("flojo")) {
                this.mWindMorning = "Weak";
            } else if (mWindMorning.equalsIgnoreCase("moderado")) {
                this.mWindMorning = "Moderate";
            }
        } else {
            this.mWindMorning = (mWindMorning.substring(0, 1).toUpperCase()) +
                    (mWindMorning.substring(1));
        }
    }

    private String getmWindAfternoon() {
        return mWindAfternoon;
    }

    public void setmWindAfternoon(String mWindAfternoon) {
        if (Locale.getDefault().getDisplayName().contains("English")) {
            if (mWindAfternoon.equalsIgnoreCase("Flojo")) {
                this.mWindAfternoon = "Weak";
            } else if (mWindAfternoon.equalsIgnoreCase("Moderado")) {
                this.mWindAfternoon = "Moderate";
            }
        } else {
            this.mWindAfternoon = mWindAfternoon.substring(0, 1).toUpperCase() +
                    (mWindAfternoon.substring(1));
        }
    }


}
