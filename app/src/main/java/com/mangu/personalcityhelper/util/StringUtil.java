package com.mangu.personalcityhelper.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.mangu.personalcityhelper.R;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @NonNull
    public static String getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        int dayOfTheMonth = cal.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(dayOfTheMonth);
    }

    @NonNull
    private static String getCurrentDayInitial() {
        Calendar cal = Calendar.getInstance();
        return cal.getDisplayName(Calendar.DAY_OF_WEEK,
                Calendar.SHORT, new Locale("es", "ES")).substring(0, 1);
    }

    @NonNull
    public static String getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        return String.valueOf(month);
    }

    @NonNull
    public static String getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        return (cal.get(Calendar.HOUR_OF_DAY) + 2) + ":" + cal.get(Calendar.MINUTE);
    }

    public static boolean isSimilarHour(String desired, String actual) {
        int minutesDesired = Integer.parseInt(desired.split(":")[1]);
        int minutesActual = Integer.parseInt(actual.split(":")[1]);
        int hourDesired = Integer.parseInt(desired.split(":")[0]);
        int hourActual = Integer.parseInt(actual.split(":")[0]);
        return (hourDesired == hourActual) &&
                (minutesActual < minutesDesired) && ((minutesDesired - minutesActual) < 20);
    }

    @NonNull
    public static String formatHourList(List<String> hours) {
        StringBuilder rtn = new StringBuilder();
        if (hours.size() == 0) {
            return "";
        }
        for (String h : hours) {
            rtn.append(h);
            if (hours.indexOf(h) != hours.size() - 1) {
                rtn.append(", ");
            }
        }
        return rtn.toString();
    }

    public static boolean isDayInFrequency(String frequency) {
        String currentDay = getCurrentDayInitial();
        if (frequency.equalsIgnoreCase("L-V")) {
            if (currentDay.equalsIgnoreCase("M") || currentDay.equalsIgnoreCase("J") ||
                    frequency.toLowerCase().contains(currentDay.toLowerCase())) {
                return true;
            }
        } else if (frequency.equalsIgnoreCase("lslab")) {
            if (currentDay.equalsIgnoreCase("M") || currentDay.equalsIgnoreCase("J") ||
                    currentDay.equalsIgnoreCase("S") ||
                    frequency.toLowerCase().contains(currentDay.toLowerCase())) {
                return true;
            }
        } else if (frequency.equalsIgnoreCase("df")) {
            if (currentDay.equalsIgnoreCase("d")) {
                return true;
            }
        } else if (frequency.equalsIgnoreCase("slab") && (currentDay.equalsIgnoreCase("s"))) {
            return true;
        }
        //TODO Saturday and Sunday
        return false;
    }

    /*
        Example: HelloWorld -> Hello World
        HellothereFriend -> Hellothere Friend
     */
    @NonNull
    static String formatCapitalized(String input) {
        StringBuilder out = new StringBuilder(input);
        Pattern p = Pattern.compile("[A-Z]");
        Matcher m = p.matcher(input);
        int extraFeed = 0;
        while (m.find()) {
            if (m.start() != 0) {
                out = out.insert(m.start() + extraFeed, " ");
                extraFeed++;
            }
        }
        return out.toString();
    }
}
