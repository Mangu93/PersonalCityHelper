package com.mangu.personalcityhelper.ui.main;

import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.inyection.ConfigPersistent;
import com.mangu.personalcityhelper.ui.base.BasePresenter;

import javax.inject.Inject;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {
    private final DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }
}
