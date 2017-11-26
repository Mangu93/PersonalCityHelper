package com.mangu.personalcityhelper.ui.transport;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.data.local.BusItem;
import com.mangu.personalcityhelper.data.model.bus.Line;
import com.mangu.personalcityhelper.data.model.bus.LineList;
import com.mangu.personalcityhelper.data.model.lineschedule.LineSchedule;
import com.mangu.personalcityhelper.data.model.lineschedule.Planificadore;
import com.mangu.personalcityhelper.ui.base.BasePresenter;
import com.mangu.personalcityhelper.util.scheduler.SchedulerUtils;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.inject.Inject;

import timber.log.Timber;

import static com.mangu.personalcityhelper.util.StringUtil.getCurrentDay;
import static com.mangu.personalcityhelper.util.StringUtil.getCurrentMonth;
import static com.mangu.personalcityhelper.util.ViewUtil.generateBusItem;
import static com.mangu.personalcityhelper.util.ViewUtil.prepareBusLayout;

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

    void processUrbanTransport(Context context, Document data) {
        checkViewAttached();
        getMvpView().showProgress(true);
        LinearLayout layout = prepareBusLayout(context, data);
    }

    void getBusLines(Context context, View.OnClickListener listener) throws IOException{
        checkViewAttached();
        getMvpView().showProgress(true);
        mDataManager.getBusLines().compose(SchedulerUtils.ioToMain())
                .subscribe(result -> processBusAdapter(result, context, listener));
    }


    private void processBusAdapter(LineList result, Context context, View.OnClickListener listener) {
        ArrayList<BusItem> busItemArrayList = new ArrayList<>();
        for (Line line: result.lineas) {
            busItemArrayList.add(generateBusItem(line));
        }
        getMvpView().changeAdapter(busItemArrayList);
    }

    void getLineSchedule(String line) {
        checkViewAttached();
        getMvpView().showProgress(true);
        mDataManager.getLineSchedule(getCurrentDay(), line, getCurrentMonth())
                .compose(SchedulerUtils.ioToMain())
                .subscribe(this::processLineSchedule);
    }

    private void processLineSchedule(LineSchedule result) {
        if (result.getPlanificadores().size() != 0) {
            Planificadore plan = result.getPlanificadores().get(0);
            try {
                getMvpView().showNextTime(plan);
            } catch (Exception e) {
                Timber.e(e);
                getMvpView().showProgress(false);
            }
        } else {
            try {
                getMvpView().showNextTime(null);
            } catch (Exception e) {
                Timber.e(e);
                getMvpView().showProgress(false);
            }
        }

    }
}
