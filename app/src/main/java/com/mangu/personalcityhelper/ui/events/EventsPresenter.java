package com.mangu.personalcityhelper.ui.events;

import android.content.Context;
import android.widget.LinearLayout;

import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.ui.base.BasePresenter;

import org.jsoup.nodes.Document;

import java.util.List;

import javax.inject.Inject;

import static com.mangu.personalcityhelper.util.ViewUtil.processDocumentIntoLayout;

public class EventsPresenter extends BasePresenter<EventsMvpView> {
    @SuppressWarnings("FieldCanBeLocal")
    private final DataManager mDataManager;

    @Inject
    public EventsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void attachView(EventsMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void showEvents(Document document, Context context) {
        checkViewAttached();
        getMvpView().showProgress(true);
        List<LinearLayout> layoutList = processDocumentIntoLayout(context, document);
        getMvpView().showProgress(false);
        getMvpView().showEvents(layoutList);
    }
}
