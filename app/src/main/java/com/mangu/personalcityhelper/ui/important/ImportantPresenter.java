package com.mangu.personalcityhelper.ui.important;

import android.content.Context;
import android.widget.LinearLayout;

import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.ui.base.BasePresenter;

import org.jsoup.nodes.Document;

import javax.inject.Inject;

import static com.mangu.personalcityhelper.util.ViewUtil.createPharmacyLayout;

public class ImportantPresenter extends BasePresenter<ImportantMvpView> {
    @SuppressWarnings("FieldCanBeLocal")
    private final DataManager mDataManager;

    @Inject
    public ImportantPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ImportantMvpView mvpView) {
        super.attachView(mvpView);
    }

    void processPharmacies(Context context, Document data) {
        checkViewAttached();
        getMvpView().showProgress(true);
        LinearLayout linearLayout = createPharmacyLayout(context, data);
        getMvpView().addLayout(linearLayout);
        getMvpView().showProgress(false);
    }
}
