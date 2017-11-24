package com.mangu.personalcityhelper.data.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mangu.personalcityhelper.ui.beach.BeachMvpView;

import org.json.JSONObject;

import javax.inject.Inject;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

import static com.mangu.personalcityhelper.util.NetworkUtil.getURLContent;

@SuppressWarnings("WeakerAccess")
public class BeachAsyncTaskLoader extends AsyncTaskLoader<JSONObject> {
    private static final String XML_URL = "http://www.aemet.es/xml/playas/play_v2_2908202.xml";
    @SuppressWarnings("CanBeFinal")
    @Inject
    BeachMvpView mBeachMvpView;
    private JSONObject mXmlDownloaded;

    public BeachAsyncTaskLoader(Context context, BeachMvpView beachMvpView) {
        super(context);
        this.mBeachMvpView = beachMvpView;
    }

    @Override
    protected void onStartLoading() {
        if (mXmlDownloaded != null) {
            deliverResult(mXmlDownloaded);
        } else {
            mBeachMvpView.showProgress(true);
            forceLoad();
        }
    }

    @Override
    public JSONObject loadInBackground() {
        String bodyString = getURLContent(XML_URL);
        XmlToJson xmlToJson = new XmlToJson.Builder(bodyString).build();
        return xmlToJson.toJson();
    }

    @Override
    public void deliverResult(JSONObject data) {
        this.mXmlDownloaded = data;
        super.deliverResult(data);
    }
}
