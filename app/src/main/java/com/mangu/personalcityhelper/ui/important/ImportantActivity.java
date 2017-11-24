package com.mangu.personalcityhelper.ui.important;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.data.remote.PharmacyAsyncTaskLoader;
import com.mangu.personalcityhelper.ui.base.BaseActivity;
import com.mangu.personalcityhelper.ui.base.ScrollViewListener;
import com.mangu.personalcityhelper.ui.common.ErrorView;
import com.mangu.personalcityhelper.ui.common.ScrollViewExt;
import com.mangu.personalcityhelper.util.NetworkUtil;
import com.mangu.personalcityhelper.util.ViewUtil;

import org.jsoup.nodes.Document;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

import static android.content.Intent.ACTION_CALL;
import static com.mangu.personalcityhelper.util.ViewUtil.createErrorSnackbar;
import static com.mangu.personalcityhelper.util.ViewUtil.createTablePhone;

@SuppressWarnings("WeakerAccess")
public class ImportantActivity extends BaseActivity implements ImportantMvpView,
        ErrorView.ErrorListener, ScrollViewListener, LoaderManager.LoaderCallbacks<Document>,
        View.OnClickListener {

    private static final int LOADER_ID = 698;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;
    @Inject
    ImportantPresenter mImportantPresenter;
    @BindView(R.id.view_error)
    ErrorView mErrorView;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.important_layout)
    LinearLayout mImportantLayout;
    ScrollViewExt mScrollView;
    @BindView(R.id.tl_telephone)
    TableLayout mTableLayout;
    private Intent mCallToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        mImportantPresenter.attachView(this);
        mScrollView = (ScrollViewExt) findViewById(R.id.scrollView);


        String[] mTelephone = getResources().getStringArray(R.array.phones);
        createTablePhone(this, mTelephone, mTableLayout, this);
        mScrollView.setScrollViewListener(this);
        mErrorView.setErrorListener(this);

        if (NetworkUtil.isNetworkConnected(this)) {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        } else {
            mErrorView.addView(ViewUtil.generateErrorLayout(this).get(0));
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_important;
    }

    @Override
    public void showProgress(boolean show) {
        mErrorView.setVisibility(View.GONE);
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorSnackMessage(Throwable errorProduced) {
        Timber.e(errorProduced);
        if (errorProduced instanceof IOException) {
            createErrorSnackbar(findViewById(getLayout())).show();
        }
    }

    @Override
    public void addLayout(LinearLayout layout) {
        mImportantLayout.addView(layout);
    }

    @Override
    public void onReloadData() {

    }

    @Override
    public void onScrollChanged(ScrollViewExt scrollView, int x, int y, int oldx, int oldy) {

    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        return new PharmacyAsyncTaskLoader(this, this);
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document data) {
        if (data != null) {
            mImportantPresenter.processPharmacies(this, data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }

    @Override
    public void onClick(View view) {
        TextView textView = (TextView) view;
        Intent callIntent = new Intent(ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + textView.getText().toString().trim()));
        mCallToDo = callIntent;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            executeCall(mCallToDo);
        }
    }

    private void executeCall(Intent mCallToDo) {
        this.runOnUiThread(() -> startActivity(mCallToDo));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    executeCall(mCallToDo);
                } else {
                    showErrorSnackMessage(new SecurityException());
                }
        }
    }
}
