package com.mangu.personalcityhelper.ui.weather;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.data.local.WeatherCardAdapter;
import com.mangu.personalcityhelper.data.local.WeatherForecastAdapter;
import com.mangu.personalcityhelper.ui.base.BaseActivity;
import com.mangu.personalcityhelper.ui.common.ErrorView;
import com.mangu.personalcityhelper.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.mangu.personalcityhelper.util.StringUtil.getDay;
import static com.mangu.personalcityhelper.util.StringUtil.isToday;

public class WeatherActivity extends BaseActivity
        implements WeatherMvpView, ErrorView.ErrorListener, View.OnClickListener {
    @Inject
    WeatherPresenter mWeatherPresenter;
    @Inject
    DataManager mDataManager;
    @BindView(R.id.view_error)
    ErrorView mErrorView;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.recycler_forecast)
    RecyclerView mRecyclerForecast;
    WeatherCardAdapter mWeatherCardAdapter;
    WeatherForecastAdapter mWeatherForecastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        mWeatherPresenter.attachView(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerForecast.setHasFixedSize(true);
        mWeatherCardAdapter = new WeatherCardAdapter(this);
        mWeatherForecastAdapter = new WeatherForecastAdapter(this);
        mWeatherForecastAdapter.setOnClickListener(this);
        mWeatherCardAdapter.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager layoutForecast = new LinearLayoutManager(getApplicationContext());
        mRecyclerForecast.setLayoutManager(layoutForecast);
        mRecyclerForecast.setAdapter(mWeatherForecastAdapter);
        mRecyclerView.setAdapter(mWeatherCardAdapter);
        mWeatherPresenter.getTodayWeather();
        mWeatherPresenter.getForecast();
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
        Timber.e(e);
        ViewUtil.createErrorSnackbar(this, getLayout());
    }

    @Override
    public void processCards(JsonObject jsonObject) {
        List<JsonObject> jsonObjectList = mWeatherCardAdapter.getJson();
        if (jsonObjectList == null) {
            jsonObjectList = new ArrayList<>();
        }
        jsonObjectList.add(jsonObject);
        mWeatherCardAdapter.setJson(jsonObjectList);
    }

    @Override
    public void processForecast(JsonObject result) {
        JsonArray jsonArray = result.getAsJsonArray("list");
        List<JsonObject> jsonList = new ArrayList<>();
        List<Integer> days = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            JsonObject object = element.getAsJsonObject();
            int dayForecast = getDay(object.get("dt").getAsInt());
            if (isToday(object.get("dt").getAsInt())) {
                continue;
            }
            if (!days.contains(dayForecast)) {
                days.add(dayForecast);
                jsonList.add(object);
            }
        }
        mWeatherForecastAdapter.setJson(jsonList);
    }

    @Override
    public void onReloadData() {

    }

    @Override
    public void onClick(View view) {

    }
}
