package com.mangu.personalcityhelper.inyection.modules;

import android.app.Application;
import android.content.Context;

import com.mangu.personalcityhelper.data.remote.BusService;
import com.mangu.personalcityhelper.data.remote.BusServiceFactory;
import com.mangu.personalcityhelper.data.remote.PlacesService;
import com.mangu.personalcityhelper.data.remote.PlacesServiceFactory;
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
    @Singleton
    static PlacesService providePlacesService() {
        return PlacesServiceFactory.makeStarterService();
    }
    @Provides
    @Singleton
    static BusService provideBusService() {
        return BusServiceFactory.makeBusService();
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
