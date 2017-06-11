package com.mangu.personalcityhelper.ui.weather;

import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.ui.base.BasePresenter;

import javax.inject.Inject;

public class WeatherPresenter extends BasePresenter<WeatherMvpView> {
    private final DataManager mDataManager;

    @Inject
    public WeatherPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(WeatherMvpView mvpView) {
        super.attachView(mvpView);
    }
}
