package com.mangu.personalcityhelper.ui.transport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.ui.base.BaseActivity;
import com.mangu.personalcityhelper.ui.common.ErrorView;
import com.mangu.personalcityhelper.ui.news.NewsPresenter;

import javax.inject.Inject;

import timber.log.Timber;

public class TransportActivity extends BaseActivity implements TransportMvpView, ErrorView.ErrorListener {

    @Inject
    TransportPresenter mTransportPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        mTransportPresenter.attachView(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_transport;
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showErrorSnackMessage(Throwable e) {
        Timber.e(e);
    }

    @Override
    public void onReloadData() {

    }
}
