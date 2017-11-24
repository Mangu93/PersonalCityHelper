package com.mangu.personalcityhelper.ui.beach;

import android.content.Context;
import android.view.View;

import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.ui.base.BasePresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.mangu.personalcityhelper.util.ViewUtil.generateBeachTextView;
import static com.mangu.personalcityhelper.util.ViewUtil.generateErrorLayout;

public class BeachPresenter extends BasePresenter<BeachMvpView> {
    @SuppressWarnings("FieldCanBeLocal")
    private final DataManager mDataManager;


    @Inject
    public BeachPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(BeachMvpView mvpView) {
        super.attachView(mvpView);
    }

    void showWeather(Context context, JSONObject jsonObject) throws JSONException {
        checkViewAttached();
        getMvpView().showProgress(true);
        JSONArray daysArray = jsonObject.getJSONObject("playa").getJSONObject("prediccion")
                .getJSONArray("dia");
        List<View> viewList = new ArrayList<>();
        viewList.add(generateBeachTextView(daysArray.getJSONObject(0), context));
        viewList.add(generateBeachTextView(daysArray.getJSONObject(1), context));
        getMvpView().drawWeather(viewList);
    }

    void showError(Context context) {
        checkViewAttached();
        getMvpView().showProgress(true);
        List<View> layoutList = new ArrayList<>();
        layoutList.add(generateErrorLayout(context).get(0));
        getMvpView().showProgress(false);
        getMvpView().drawWeather(layoutList);
    }
}
