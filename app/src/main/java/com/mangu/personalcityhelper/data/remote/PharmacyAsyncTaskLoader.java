package com.mangu.personalcityhelper.data.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mangu.personalcityhelper.ui.important.ImportantMvpView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

import timber.log.Timber;

@SuppressWarnings("WeakerAccess")
public class PharmacyAsyncTaskLoader extends AsyncTaskLoader<Document> {
    @SuppressWarnings("FieldCanBeLocal")
    private final String mBaseUrl = "http://farmaciasguardia.portalfarma.com/web_guardias/Guardias.asp?date=13/10/2017&vzona=29000014&vmenu=1&provincia=29";

    @SuppressWarnings("CanBeFinal")
    @Inject
    ImportantMvpView mMvpView;
    private Document mHtmlDocument;

    public PharmacyAsyncTaskLoader(Context context, ImportantMvpView mMvpView) {
        super(context);
        this.mMvpView = mMvpView;
    }

    @Override
    public Document loadInBackground() {
        try {
            return Jsoup.parse(new URL(mBaseUrl).openStream(), "ISO-8859-1", mBaseUrl);
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
