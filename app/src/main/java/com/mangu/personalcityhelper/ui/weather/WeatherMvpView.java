package com.mangu.personalcityhelper.ui.weather;

import com.google.gson.JsonObject;
import com.mangu.personalcityhelper.ui.base.MvpView;

public interface WeatherMvpView extends MvpView {
    void showProgress(boolean show);

    void showErrorSnackMessage(Throwable e);

    void processCards(JsonObject jsonObject);

    void processForecast(JsonObject result);
}
