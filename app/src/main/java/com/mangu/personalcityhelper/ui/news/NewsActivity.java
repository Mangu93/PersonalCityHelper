package com.mangu.personalcityhelper.ui.news;

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

public class NewsActivity extends BaseActivity implements
        NewsMvpView, ErrorView.ErrorListener, LoaderCallbacks<Document>, ScrollViewListener {
    private static final int LOADER_ID = 101;
    @Inject
    NewsPresenter mNewsPresenter;
    @BindView(R.id.view_error)
    ErrorView mErrorView;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.news_layout)
    LinearLayout mNewsLayout;

    ScrollViewExt mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        mNewsPresenter.attachView(this);
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
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOADER_ID);
        mNewsPresenter.detachView();
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
    public void showNews(List<LinearLayout> layoutList) {
        for (LinearLayout layout : layoutList) {
            mNewsLayout.addView(layout);
        }
    }

    @Override
    public void onReloadData() {

    }

    @Override
    public Loader<Document> onCreateLoader(int i, Bundle bundle) {
        return new NewsAsyncTaskLoader(this, this);
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document document) {
        if (document != null) {
            mNewsPresenter.showNews(document, this);
        } else {
            //show error???
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        getSupportLoaderManager().destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {
        ((NewsAsyncTaskLoader) loader).rollbackOnStop();
    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        if (diff == 0) {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        }

    }
}
