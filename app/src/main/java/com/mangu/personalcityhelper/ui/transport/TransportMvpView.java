package com.mangu.personalcityhelper.ui.transport;

import com.mangu.personalcityhelper.ui.base.MvpView;

public interface TransportMvpView extends MvpView {
    void showProgress(boolean show);

    void showErrorSnackMessage(Throwable e);
}
