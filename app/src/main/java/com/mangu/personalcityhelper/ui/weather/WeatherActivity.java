package com.mangu.personalcityhelper.ui.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.ui.base.BaseActivity;
import com.mangu.personalcityhelper.ui.common.ErrorView;

public class WeatherActivity extends BaseActivity
        implements WeatherMvpView, ErrorView.ErrorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_weather;
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showErrorSnackMessage(Throwable e) {

    }

    @Override
    public void onReloadData() {

    }
}
