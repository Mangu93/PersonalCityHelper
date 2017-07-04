package com.mangu.personalcityhelper.ui.weather;

import android.util.Log;

import com.google.gson.JsonObject;
import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.ui.base.BasePresenter;
import com.mangu.personalcityhelper.util.scheduler.SchedulerUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

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

    void getTodayWeather() {
        checkViewAttached();
        getMvpView().showProgress(true);
        Observable<JsonObject> stringObservable = mDataManager.getWeatherForToday();
        stringObservable.compose(SchedulerUtils.ioToMain())
                .subscribe(result -> {
                    Log.i("TAG", result.toString());
                    getMvpView().processCards(result);
                }, Timber::e);
    }

    void getForecast() {
        checkViewAttached();
        getMvpView().showProgress(true);
        Observable<JsonObject> stringObservable = mDataManager.getForecast();
        stringObservable.compose(SchedulerUtils.ioToMain())
                .subscribe(result -> {
                    Log.i("TAG", result.toString());
                    getMvpView().processForecast(result);
                }, Timber::e);
    }
}
