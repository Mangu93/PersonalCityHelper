package com.mangu.personalcityhelper.ui.beach;

import android.view.View;

import com.mangu.personalcityhelper.ui.base.MvpView;

import org.json.JSONObject;

import java.util.List;

public interface BeachMvpView extends MvpView {
    void showProgress(boolean show);

    void showErrorSnackMessage(Throwable e);

    void showWeather(JSONObject jsonObject);

    void drawWeather(List<View> viewList);
}
