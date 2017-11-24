package com.mangu.personalcityhelper.ui.important;

import android.widget.LinearLayout;

import com.mangu.personalcityhelper.ui.base.MvpView;

public interface ImportantMvpView extends MvpView {
    void showProgress(boolean show);

    void showErrorSnackMessage(Throwable e);

    void addLayout(LinearLayout layout);
}
