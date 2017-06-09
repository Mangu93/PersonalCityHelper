package com.mangu.personalcityhelper.ui.events;

import android.widget.LinearLayout;

import com.mangu.personalcityhelper.ui.base.MvpView;

import java.util.List;

public interface EventsMvpView extends MvpView {
    void showProgress(boolean show);

    void showErrorSnackMessage(Throwable e);

    void showEvents(List<LinearLayout> layoutList);
}
