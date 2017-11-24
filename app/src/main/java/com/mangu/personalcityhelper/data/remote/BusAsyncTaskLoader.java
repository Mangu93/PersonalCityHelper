package com.mangu.personalcityhelper.data.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mangu.personalcityhelper.ui.transport.TransportMvpView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import javax.inject.Inject;

import timber.log.Timber;

@SuppressWarnings("ALL")
public class BusAsyncTaskLoader extends AsyncTaskLoader<Document> {

    private static final String BASE_URL = "http://www.rincondelavictoria.es/" +
            "via-publica-movilidad-y-transporte/horario-y-lineas-de-autobuses-urbanos";
    @Inject
    TransportMvpView mMvpView;
    private Document mHtmlDocument;

    public BusAsyncTaskLoader(Context context, TransportMvpView mvpView) {
        super(context);
        this.mMvpView = mvpView;
    }

    @Override
    public Document loadInBackground() {
        try {
            return Jsoup.connect(BASE_URL).get();
        } catch (IOException ex) {
            Timber.e(ex);
            mMvpView.showProgress(false);
            mMvpView.showErrorSnackMessage(ex);
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        if (mHtmlDocument != null) {
            deliverResult(mHtmlDocument);
        } else {
            mMvpView.showProgress(true);
            forceLoad();
        }
    }

    @Override
    public void deliverResult(Document data) {
        this.mHtmlDocument = data;
        super.deliverResult(data);

    }
}
