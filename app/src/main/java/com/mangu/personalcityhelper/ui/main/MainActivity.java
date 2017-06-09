package com.mangu.personalcityhelper.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.ui.base.BaseActivity;
import com.mangu.personalcityhelper.ui.beach.BeachActivity;
import com.mangu.personalcityhelper.ui.common.ErrorView;
import com.mangu.personalcityhelper.ui.events.EventsActivity;
import com.mangu.personalcityhelper.ui.news.NewsActivity;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView, ErrorView.ErrorListener {
    @Inject
    MainPresenter mMainPresenter;


    @BindView(R.id.view_error)
    ErrorView mErrorView;
    @BindView(R.id.btn_change_language)
    Button btn_language;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        mMainPresenter.attachView(this);
        mErrorView.setErrorListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.btn_change_language})
    public void changeLanguage(View view) {
        Locale current = Locale.getDefault();
        if (current.getLanguage().contains("en")) {
            createChangeLanguageDialog(this, "Change", "Spanish").show();
        } else {
            createChangeLanguageDialog(this, "Cambiar", "Inglés").show();
        }

    }

    @OnClick({R.id.id_beach, R.id.id_news, R.id.id_weather, R.id.id_events})
    public void onClickTarget(View view) {
        switch (view.getId()) {

            case R.id.id_beach:
                startActivity(new Intent(this, BeachActivity.class));
                break;
            case R.id.id_news:
                startActivity(new Intent(this, NewsActivity.class));
                break;
            case R.id.id_weather:
                break;
            case R.id.id_events:
                startActivity(new Intent(this, EventsActivity.class));
                break;
            default:
                Timber.e("Unexpected view received", view);
        }
    }

    @Override
    public void showError(Throwable error) {
        Timber.e(error, "There was an error");
    }

    @Override
    public void onReloadData() {

    }

    private Dialog createChangeLanguageDialog(Context context, String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setPositiveButton(message, (dialogInterface, i) -> {
                    Locale locale;
                    if (message.equalsIgnoreCase("Inglés")) {
                        locale = new Locale("en", "us");
                    } else {
                        locale = new Locale("es", "es");
                    }
                    Locale.setDefault(locale);
                    changeLocale(locale);
                });
        return dialog.create();
    }

    private void changeLocale(Locale locale) {
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLayoutDirection(config.locale);
        }
        getApplicationContext().getResources().updateConfiguration(config, null);
        recreate();

    }

}
