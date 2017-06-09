package com.mangu.personalcityhelper.ui.news;

import android.widget.LinearLayout;

import com.mangu.personalcityhelper.ui.base.MvpView;

import java.util.List;

public interface NewsMvpView extends MvpView {

    void showProgress(boolean show);

    void showErrorSnackMessage(Throwable e);

    void showNews(List<LinearLayout> layoutList);
}
