package com.mangu.personalcityhelper.ui.events;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.data.remote.NewsAsyncTaskLoader;
import com.mangu.personalcityhelper.ui.base.BaseActivity;
import com.mangu.personalcityhelper.ui.base.ScrollViewListener;
import com.mangu.personalcityhelper.ui.common.ErrorView;
import com.mangu.personalcityhelper.ui.common.ScrollViewExt;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.mangu.personalcityhelper.util.ViewUtil.createErrorSnackbar;

public class EventsActivity extends BaseActivity
        implements EventsMvpView, ErrorView.ErrorListener, ScrollViewListener,
        LoaderCallbacks<Document> {

    private static final int LOADER_ID = 201;
    @Inject
    EventsPresenter mEventsPresenter;
    @BindView(R.id.view_error)
    ErrorView mErrorView;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.news_layout)
    LinearLayout mEventsLayout;
    ScrollViewExt mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        mEventsPresenter.attachView(this);
        mScrollView = (ScrollViewExt) findViewById(R.id.scrollView);
        mScrollView.setScrollViewListener(this);
        mErrorView.setErrorListener(this);
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_news;
    }

    @Override
    public void onReloadData() {

    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        if (diff == 0) {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public void showProgress(boolean show) {
        mErrorView.setVisibility(View.GONE);
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorSnackMessage(Throwable errorProduced) {
        if (errorProduced instanceof IOException) {
            createErrorSnackbar(getApplicationContext(), getLayout()).show();
        }
    }

    @Override
    public void showEvents(List<LinearLayout> layoutList) {
        for (LinearLayout layout : layoutList) {
            mEventsLayout.addView(layout);
        }
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new NewsAsyncTaskLoader(this, this,
                "http://www.rincondelavictoria.es/agenda/page/");
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document data) {
        if (data != null) {
            mEventsPresenter.showEvents(data, this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        getSupportLoaderManager().destroyLoader(LOADER_ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventsPresenter.detachView();
    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {
        NewsAsyncTaskLoader.rollbackOnStop();
    }

}
