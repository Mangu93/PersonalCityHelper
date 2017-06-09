package com.mangu.personalcityhelper;


import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.mangu.personalcityhelper.inyection.components.AppComponent;
import com.mangu.personalcityhelper.inyection.components.DaggerAppComponent;
import com.mangu.personalcityhelper.inyection.modules.AppModule;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class StarterApp extends MultiDexApplication {
    AppComponent mApplicationComponent;

    public static StarterApp get(Context context) {
        return (StarterApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            LeakCanary.install(this);
        }
    }

    public AppComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();

        }
        return mApplicationComponent;
    }

    public void setComponent(AppComponent appComponent) {
        mApplicationComponent = appComponent;
    }
}
