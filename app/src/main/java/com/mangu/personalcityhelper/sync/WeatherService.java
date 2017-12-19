package com.mangu.personalcityhelper.sync;


import android.content.Context;
import android.util.Log;


import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.mangu.personalcityhelper.util.NotificationUtils;


public class WeatherService extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {
        Context context = getApplicationContext();
        NotificationUtils.createWeatherNotification(context);
        Log.i("WeatherService", "Notification showed");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
