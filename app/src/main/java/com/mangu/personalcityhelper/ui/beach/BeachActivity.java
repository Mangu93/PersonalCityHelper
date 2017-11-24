package com.mangu.personalcityhelper.ui.beach;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.data.remote.BeachAsyncTaskLoader;
import com.mangu.personalcityhelper.ui.base.BaseActivity;
import com.mangu.personalcityhelper.ui.common.ErrorView;
import com.mangu.personalcityhelper.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.mangu.personalcityhelper.util.ViewUtil.createErrorSnackbar;

@SuppressWarnings("WeakerAccess")
public class BeachActivity extends BaseActivity implements
        BeachMvpView, ErrorView.ErrorListener, LoaderCallbacks<JSONObject> {
    private static final int LOADER_ID = 202;
    @Inject
    BeachPresenter mBeachPresenter;
    @BindView(R.id.view_error)
    ErrorView mErrorView;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        mBeachPresenter.attachView(this);
        mErrorView.setErrorListener(this);
        startDownload();
    }

    private void startDownload() {
        if (NetworkUtil.isNetworkConnected(this)) {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        } else {
            mBeachPresenter.showError(this);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_beach;
    }

    @Override
    public void showProgress(boolean show) {
        mErrorView.setVisibility(View.GONE);
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorSnackMessage(Throwable errorProduced) {
        if (errorProduced instanceof IOException) {
            createErrorSnackbar(this, getLayout()).show();
            Timber.e(errorProduced);
        } else {
            Timber.e(errorProduced);
        }
    }

    @Override
    public void showWeather(JSONObject jsonObject) {
        try {
            mBeachPresenter.showWeather(this, jsonObject);
        } catch (JSONException ex) {
            Timber.e(ex);
        }
    }

    @Override
    public void drawWeather(List<View> viewList) {
        for (View view : viewList) {
            mLinearLayout.addView(view);
            showProgress(false);
        }
        mLinearLayout.getBackground().setAlpha(80);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBeachPresenter.detachView();
    }

    @Override
    public void onReloadData() {

    }


    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
        return new BeachAsyncTaskLoader(this, this);
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
        showWeather(data);
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {

    }
}
