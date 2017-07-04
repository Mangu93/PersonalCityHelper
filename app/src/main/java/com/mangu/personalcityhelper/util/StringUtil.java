package com.mangu.personalcityhelper.util;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mangu.personalcityhelper.R;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

public class StringUtil {
    static String[] beachStrings(Context context) {
        String[] neededStrings = new String[10];
        neededStrings[0] = context.getString(R.string.day);
        neededStrings[1] = context.getString(R.string.waterTemp);
        neededStrings[2] = context.getString(R.string.maxTemp);
        neededStrings[3] = context.getString(R.string.swell);
        neededStrings[4] = context.getString(R.string.wind);
        neededStrings[5] = context.getString(R.string.thermalSens);
        neededStrings[6] = context.getString(R.string.uvMax);
        neededStrings[7] = context.getString(R.string.sky);
        neededStrings[8] = context.getString(R.string.morning);
        neededStrings[9] = context.getString(R.string.afternoon);

        return neededStrings;
    }

    public static String kelvinToCelsius(String kelvinTemperature) {
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        Double value = Double.parseDouble(kelvinTemperature) - 273.15d;
        return twoDecimals.format(value);
    }

    public static boolean isToday(int timestamp) {
        Calendar toCheck = getCalendar(timestamp);
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DAY_OF_MONTH) == toCheck.get(Calendar.DAY_OF_MONTH)
                && now.get(Calendar.MONTH) == toCheck.get(Calendar.MONTH)
                && now.get(Calendar.YEAR) == toCheck.get(Calendar.YEAR);
    }

    public static String getMinAndMax(JsonObject element) {
        String min = kelvinToCelsius(element.get("temp_min").toString());
        String max = kelvinToCelsius(element.get("temp_max").toString());
        return min + "ºC" + " -> " + max + "ºC";
    }

    public static String formatTimestamp(int timestamp) {
        Calendar calendar = getCalendar(timestamp);
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1)
                + "/" + calendar.get(Calendar.YEAR) + ", " +
                calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }

    public static int getDay(int timestamp) {
        Calendar calendar = getCalendar(timestamp);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    private static Calendar getCalendar(int timestamp) {
        java.util.Date time = new java.util.Date((long) timestamp * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar;
    }
}
