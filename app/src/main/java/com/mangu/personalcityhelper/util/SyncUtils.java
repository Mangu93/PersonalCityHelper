package com.mangu.personalcityhelper.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mangu.personalcityhelper.BuildConfig;
import com.mangu.personalcityhelper.sync.WeatherService;

import static java.util.concurrent.TimeUnit.HOURS;

public class SyncUtils {

    private static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context)  {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job syncJob = dispatcher.newJobBuilder()
                .setService(WeatherService.class)
                .setTag("wnot-tag")
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setTrigger(Trigger.executionWindow(
                        (int)HOURS.toSeconds(10), (int) HOURS.toSeconds(30)))
                .build();
        Log.i("SyncUtils", "Firebase job dispatcher");
        dispatcher.mustSchedule(syncJob);
    }

    public static void scheduleFirebaseTest(@NonNull Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job syncJob = dispatcher.newJobBuilder()
                .setService(WeatherService.class)
                .setTag("wnot-test-tag")
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setTrigger(Trigger.NOW)
                .build();
        Log.i("SyncUtils", "Firebase Test job dispatcher");
        dispatcher.mustSchedule(syncJob);
    }

    synchronized public static void initialize(@NonNull final Context context) {
        if (isGooglePlayServiceAvailable(context)) {
            scheduleFirebaseJobDispatcherSync(context);
            if(BuildConfig.DEBUG) {
                scheduleFirebaseTest(context);
            }
        }
    }

    private static boolean isGooglePlayServiceAvailable(Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }
}
