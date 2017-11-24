package com.mangu.personalcityhelper.ui.weather;

import android.view.View;

import com.google.gson.JsonObject;
import com.mangu.personalcityhelper.ui.base.MvpView;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public interface WeatherMvpView extends MvpView {
    void showProgress(boolean show);

    void showErrorSnackMessage(Throwable e);

    void processCards(JsonObject jsonObject);

    void processForecast(JsonObject result);

    void showError(List<View> viewList);
}
