package com.mangu.personalcityhelper.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.ui.base.BaseActivity;
import com.mangu.personalcityhelper.ui.beach.BeachActivity;
import com.mangu.personalcityhelper.ui.common.ErrorView;
import com.mangu.personalcityhelper.ui.events.EventsActivity;
import com.mangu.personalcityhelper.ui.news.NewsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView, ErrorView.ErrorListener {
    @Inject
    MainPresenter mMainPresenter;


    @BindView(R.id.view_error)
    ErrorView mErrorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        mMainPresenter.attachView(this);
        mErrorView.setErrorListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }


    @OnClick({R.id.id_beach, R.id.id_news, R.id.id_weather, R.id.id_events})
    public void onClickTarget(View view) {
        switch (view.getId()) {

            case R.id.id_beach:
                startActivity(new Intent(this, BeachActivity.class));
                break;
            case R.id.id_news:
                startActivity(new Intent(this, NewsActivity.class));
                break;
            case R.id.id_weather:
                break;
            case R.id.id_events:
                startActivity(new Intent(this, EventsActivity.class));
                break;
            default:
                Timber.e("Unexpected view received", view);
        }
    }

    @Override
    public void showError(Throwable error) {
        Timber.e(error, "There was an error");
    }

    @Override
    public void onReloadData() {

    }


}
