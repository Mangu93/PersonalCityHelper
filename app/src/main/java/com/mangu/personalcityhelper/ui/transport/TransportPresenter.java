package com.mangu.personalcityhelper.ui.transport;

import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.ui.base.BasePresenter;

import javax.inject.Inject;

public class TransportPresenter extends BasePresenter<TransportMvpView> {
    private final DataManager mDataManager;

    @Inject
    TransportPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(TransportMvpView mvpView) {
        super.attachView(mvpView);
    }
}
