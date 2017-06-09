package com.mangu.personalcityhelper.inyection.components;

import android.app.Application;
import android.content.Context;

import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.data.remote.StarterService;
import com.mangu.personalcityhelper.inyection.AppContext;
import com.mangu.personalcityhelper.inyection.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @AppContext
    Context context();

    Application application();

    DataManager dataManager();

    StarterService starterService();

}
