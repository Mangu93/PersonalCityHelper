package com.mangu.personalcityhelper.ui.transport;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.mangu.personalcityhelper.data.DataManager;
import com.mangu.personalcityhelper.data.model.bus.Line;
import com.mangu.personalcityhelper.data.model.bus.LineList;
import com.mangu.personalcityhelper.data.model.lineschedule.LineSchedule;
import com.mangu.personalcityhelper.data.model.lineschedule.Planificadore;
import com.mangu.personalcityhelper.ui.base.BasePresenter;
import com.mangu.personalcityhelper.util.scheduler.SchedulerUtils;

import org.jsoup.nodes.Document;

import java.net.UnknownHostException;

import javax.inject.Inject;

import timber.log.Timber;

import static com.mangu.personalcityhelper.util.StringUtil.getCurrentDay;
import static com.mangu.personalcityhelper.util.StringUtil.getCurrentMonth;
import static com.mangu.personalcityhelper.util.ViewUtil.dpToPx;
import static com.mangu.personalcityhelper.util.ViewUtil.generateBusLink;
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

    void getBusLines(Context context, View.OnClickListener listener) throws UnknownHostException{
        checkViewAttached();
        getMvpView().showProgress(true);
        mDataManager.getBusLines().compose(SchedulerUtils.ioToMain())
                .subscribe(result -> processBusLines(result, context, listener));
    }

    private void processBusLines(LineList result, Context context, View.OnClickListener listener) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        for (Line line : result.lineas) {
            linearLayout.addView(generateBusLink(context, line, listener));
        }
        getMvpView().addBusView(linearLayout);
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
