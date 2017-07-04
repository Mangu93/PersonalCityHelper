package com.mangu.personalcityhelper.inyection.modules;

import android.app.Application;
import android.content.Context;

import com.mangu.personalcityhelper.data.remote.WeatherService;
import com.mangu.personalcityhelper.data.remote.WeatherServiceFactory;
import com.mangu.personalcityhelper.inyection.AppContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    static WeatherService provideStarterService() {
        return WeatherServiceFactory.makeStarterService();
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @AppContext
    Context provideContext() {
        return mApplication;
    }

}
