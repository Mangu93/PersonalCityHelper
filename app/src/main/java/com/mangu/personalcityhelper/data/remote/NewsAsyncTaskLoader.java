package com.mangu.personalcityhelper.data.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mangu.personalcityhelper.ui.events.EventsMvpView;
import com.mangu.personalcityhelper.ui.news.NewsMvpView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import javax.inject.Inject;

import timber.log.Timber;

public class NewsAsyncTaskLoader extends AsyncTaskLoader<Document> {
    private static int count = 1;
    @Inject
    NewsMvpView mvpView;
    @Inject
    EventsMvpView eventsMvpView;
    private String mBaseNewsUrl = "http://www.rincondelavictoria.es/noticias/page/";
    private Context mContext;
    private Document mHtmlDocument;

    public NewsAsyncTaskLoader(Context context, NewsMvpView mvpView) {
        super(context);
        this.mContext = context;
        this.mvpView = mvpView;
    }

    public NewsAsyncTaskLoader(Context context, EventsMvpView eventsMvpView, String url) {
        super(context);
        this.mContext = context;
        this.eventsMvpView = eventsMvpView;
        this.mBaseNewsUrl = url;
    }

    /*
        I mean, I'm sure I should not be doing something like this
        But idk.
     */
    public void rollbackOnStop() {
        count = 1;
        mHtmlDocument = null;
    }

    @Override
    protected void onStartLoading() {
        if (mHtmlDocument != null) {
            deliverResult(mHtmlDocument);
        } else {
            if (mvpView != null) {
                mvpView.showProgress(true);
                forceLoad();
            } else if (eventsMvpView != null) {
                eventsMvpView.showProgress(true);
                forceLoad();
            }
        }
    }

    @Override
    public Document loadInBackground() {
        try {
            String newsUrl = mBaseNewsUrl + count;
            Document document = Jsoup.connect(newsUrl).get();
            count = count + 1;
            return document;
        } catch (IOException e) {
            if (mvpView != null) {
                mvpView.showProgress(false);
                mvpView.showErrorSnackMessage(e);
            } else if (eventsMvpView != null) {
                eventsMvpView.showProgress(false);
                eventsMvpView.showErrorSnackMessage(e);
            }
            Timber.e(e);

        }
        return null;
    }

    @Override
    public void deliverResult(Document data) {
        mHtmlDocument = data;
        super.deliverResult(mHtmlDocument);
    }
}
