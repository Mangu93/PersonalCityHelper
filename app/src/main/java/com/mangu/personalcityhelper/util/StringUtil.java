package com.mangu.personalcityhelper.util;

import android.content.Context;

import com.mangu.personalcityhelper.R;

class StringUtil {
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
        neededStrings[8] = context.getString(R.string.tomorrow);
        neededStrings[9] = context.getString(R.string.morning);

        return neededStrings;
    }
}
