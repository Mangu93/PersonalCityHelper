package com.mangu.personalcityhelper.data;

import com.mangu.personalcityhelper.data.remote.StarterService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {
    private final StarterService mStarterService;

    @Inject
    public DataManager(StarterService starterService) {
        this.mStarterService = starterService;
    }

}
