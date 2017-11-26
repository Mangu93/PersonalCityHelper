package com.mangu.personalcityhelper.ui.transport;

import android.widget.LinearLayout;

import com.mangu.personalcityhelper.data.local.BusItem;
import com.mangu.personalcityhelper.data.model.lineschedule.Planificadore;
import com.mangu.personalcityhelper.ui.base.MvpView;

import java.util.ArrayList;

public interface TransportMvpView extends MvpView {
    void showProgress(boolean show);

    void showErrorSnackMessage(Throwable e);

    void addBusView(LinearLayout linearLayout);

    void showNextTime(Planificadore plan) throws Exception;

    void changeAdapter(ArrayList<BusItem> busItemArrayList);
}
