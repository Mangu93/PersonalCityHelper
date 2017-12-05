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

@SuppressWarnings({"SameParameterValue", "WeakerAccess"})
public class NewsAsyncTaskLoader extends AsyncTaskLoader<Document> {
    private static int mCount = 1;
    @Inject
    NewsMvpView mvpView;
    @Inject
    EventsMvpView eventsMvpView;
    private String mBaseNewsUrl = "http://www.rincondelavictoria.es/noticias/page/";
    private Document mHtmlDocument;

    public NewsAsyncTaskLoader(Context context, NewsMvpView mvpView) {
        super(context);
        this.mvpView = mvpView;
    }

    public NewsAsyncTaskLoader(Context context, EventsMvpView eventsMvpView, String url) {
        super(context);
        this.eventsMvpView = eventsMvpView;
        this.mBaseNewsUrl = url;
    }

    /*
        I mean, I'm sure I should not be doing something like this
        But idk.
     */
    public static void rollbackOnStop() {
        NewsAsyncTaskLoader.mCount = 1;
        //mHtmlDocument = null;
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
            String newsUrl = mBaseNewsUrl + mCount;
            Document document = Jsoup.connect(newsUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; " +
                            "WOW64; rv:21.0) Gecko/20100101 Firefox/21.0")
                    .header("Accept-Language", "en")
                    .header("Accept-Encoding", "gzip,deflate,sdch")
                    .get();
            mCount = mCount + 1;
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
