package com.mangu.personalcityhelper.ui.transport;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.data.local.BusAdapter;
import com.mangu.personalcityhelper.data.local.BusItem;
import com.mangu.personalcityhelper.data.model.lineschedule.HorarioIda;
import com.mangu.personalcityhelper.data.model.lineschedule.Planificadore;
import com.mangu.personalcityhelper.data.remote.BusAsyncTaskLoader;
import com.mangu.personalcityhelper.ui.base.BaseActivity;
import com.mangu.personalcityhelper.ui.common.ErrorView;
import com.mangu.personalcityhelper.util.NetworkUtil;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static com.mangu.personalcityhelper.util.StringUtil.formatHourList;
import static com.mangu.personalcityhelper.util.StringUtil.getCurrentHour;
import static com.mangu.personalcityhelper.util.StringUtil.isDayInFrequency;
import static com.mangu.personalcityhelper.util.StringUtil.isSimilarHour;
import static com.mangu.personalcityhelper.util.ViewUtil.createErrorSnackbar;
import static com.mangu.personalcityhelper.util.ViewUtil.generateErrorLayout;

@SuppressWarnings("WeakerAccess")
public class TransportActivity extends BaseActivity
        implements TransportMvpView, ErrorView.ErrorListener,
        LoaderCallbacks<Document>, View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    TransportPresenter mTransportPresenter;
    @BindView(R.id.view_error)
    ErrorView mViewError;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.relative)
    RelativeLayout mRelativeLayout;
    ArrayList<BusItem> mBusItems = new ArrayList<>();
    ListView mListView;
    BusAdapter mBusAdapter;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = (ListView) findViewById(R.id.listView);
        activityComponent().inject(this);
        mTransportPresenter.attachView(this);

        if (NetworkUtil.isNetworkConnected(getApplicationContext())) {
            try {
                mTransportPresenter.getBusLines(getApplicationContext(), this);
            } catch (IOException ex) {
                showErrorSnackMessage(ex);
                mViewError.addView(generateErrorLayout(getApplicationContext()).get(0));
                mViewError.setVisibility(View.VISIBLE);
            }
        } else {
            mViewError.addView(generateErrorLayout(getApplicationContext()).get(0));
            mViewError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_transport;
    }

    @Override
    public void showProgress(boolean show) {
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorSnackMessage(Throwable e) {
        Timber.e(e);
        if (e instanceof IOException) {
            createErrorSnackbar(this.mRelativeLayout).show();
        }
    }

    @Override
    public void addBusView(LinearLayout linearLayout) {
        mRelativeLayout.addView(linearLayout);
        showProgress(false);
    }

    @Override
    public void showNextTime(Planificadore plan) throws Exception {
        if (plan == null) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.error_no_data, Snackbar.LENGTH_LONG).show();
        } else {
            List<HorarioIda> ida = plan.getHorarioIda();
            List<String> times = new ArrayList<>();
            String actualHour = getCurrentHour();
            for (HorarioIda horarioIda : ida) {
                if (isDayInFrequency(horarioIda.getFrecuencia())) {
                    for (String hora : horarioIda.getHoras()) {
                        if (times.size() == 4) {
                            break;
                        }
                        if (hora.equalsIgnoreCase(actualHour) || isSimilarHour(hora, actualHour)) {
                            times.add(hora);
                        }
                    }
                }
                if (times.size() == 4) {
                    break;
                }
            }
            String message = formatHourList(times);
            if (message.equalsIgnoreCase("")) {
                message = getString(R.string.error_no_data);
            } else {
                message = getString(R.string.next) + message;
            }
            Snackbar.make(findViewById(android.R.id.content), message,
                    Snackbar.LENGTH_LONG).show();
        }
        showProgress(false);
    }

    @Override
    public void changeAdapter(ArrayList<BusItem> busItemArrayList) {
        mBusAdapter = new BusAdapter(this, busItemArrayList);
        mListView.setAdapter(mBusAdapter);
        mListView.setOnItemClickListener(this);
        showProgress(false);
        mToast = Toast.makeText(this, getString(R.string.more_details), Toast.LENGTH_SHORT);
        mToast.show();
    }


    @Override
    public void onReloadData() {

    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new BusAsyncTaskLoader(this, this);
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document data) {
        if (data != null) {
            mTransportPresenter.processUrbanTransport(getApplicationContext(), data);
        } else {
            Timber.e("Loader data is null", loader);
        }
    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }

    @Override
    public void onClick(View view) {
        String text = ((TextView) view).getText().toString();
        String id = text.split(":")[0];
        mTransportPresenter.getLineSchedule(id);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mToast != null) {
            mToast.cancel();
        }
        TextView textView = (TextView) view.findViewById(R.id.line_id);
        String line = textView.getText().toString();
        mTransportPresenter.getLineSchedule(line);
        showProgress(false);
    }
}
