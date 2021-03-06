package com.mangu.personalcityhelper.inyection.modules;

import android.app.Activity;
import android.content.Context;

import com.mangu.personalcityhelper.inyection.ActivityContext;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("ALL")
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }
}
