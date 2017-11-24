package com.mangu.personalcityhelper.ui.main;


import com.mangu.personalcityhelper.ui.base.MvpView;

@SuppressWarnings("WeakerAccess")
public interface MainMvpView extends MvpView {


    void showError(Throwable error);
}
