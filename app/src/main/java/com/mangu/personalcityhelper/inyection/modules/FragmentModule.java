package com.mangu.personalcityhelper.inyection.modules;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.mangu.personalcityhelper.inyection.ActivityContext;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("CanBeFinal")
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment providesFragment() {
        return mFragment;
    }

    @Provides
    Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mFragment.getActivity();
    }

}