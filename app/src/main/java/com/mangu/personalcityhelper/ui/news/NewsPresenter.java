package com.mangu.personalcityhelper.ui.news;

import android.content.Context;
import android.widget.LinearLayout;

import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.inyection.ConfigPersistent;
import com.mangu.personalcityhelper.ui.base.BasePresenter;

import org.jsoup.nodes.Document;

import java.util.List;

import javax.inject.Inject;

import static com.mangu.personalcityhelper.util.ViewUtil.processDocumentIntoLayout;

@ConfigPersistent
public class NewsPresenter extends BasePresenter<NewsMvpView> {
    private final DataManager mDataManager;


    @Inject
    public NewsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(NewsMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void showNews(Document document, Context context) {
        checkViewAttached();
        getMvpView().showProgress(true);
        List<LinearLayout> layoutList = processDocumentIntoLayout(context, document);
        getMvpView().showProgress(false);
        getMvpView().showNews(layoutList);
    }

}
