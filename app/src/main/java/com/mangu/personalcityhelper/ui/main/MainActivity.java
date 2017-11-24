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
import com.mangu.personalcityhelper.ui.important.ImportantActivity;
import com.mangu.personalcityhelper.ui.news.NewsActivity;
import com.mangu.personalcityhelper.ui.transport.TransportActivity;
import com.mangu.personalcityhelper.ui.weather.WeatherActivity;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

@SuppressWarnings("WeakerAccess")
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

    @OnClick({R.id.id_beach, R.id.id_news, R.id.id_weather, R.id.id_events, R.id.id_transport,
            R.id.textViewBeach, R.id.textViewCalendar, R.id.textViewNews, R.id.textViewTransport,
            R.id.textViewWeather, R.id.id_important, R.id.textViewImportant})
    public void onClickTarget(View view) {
        switch (view.getId()) {

            case R.id.id_beach:
            case R.id.textViewBeach:
                startActivity(new Intent(this, BeachActivity.class));
                break;
            case R.id.id_news:
            case R.id.textViewNews:
                startActivity(new Intent(this, NewsActivity.class));
                break;
            case R.id.id_weather:
            case R.id.textViewWeather:
                startActivity(new Intent(this, WeatherActivity.class));
                break;
            case R.id.id_events:
            case R.id.textViewCalendar:
                startActivity(new Intent(this, EventsActivity.class));
                break;
            case R.id.id_transport:
            case R.id.textViewTransport:
                startActivity(new Intent(this, TransportActivity.class));
                break;
            case R.id.id_important:
            case R.id.textViewImportant:
                startActivity(new Intent(this, ImportantActivity.class));
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

    @SuppressWarnings("deprecation")
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
