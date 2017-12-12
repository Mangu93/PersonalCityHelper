package com.mangu.personalcityhelper.data.remote;


import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;


public class PlacesServiceFactory {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";

    public static PlacesService makeStarterService() {
        return makeStarterService(makeGson());
    }

    private static PlacesService makeStarterService(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(makeOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(PlacesService.class);
    }

    @NonNull
    private static OkHttpClient makeOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message
                -> Timber.d(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.addInterceptor(chain -> {
            Request request = chain.request();
            HttpUrl originalHttpUrl = request.url();
            HttpUrl httpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", "AIzaSyCYPsYO3wSP-Z_3SFvvUlShSOQbKL-1ENI").build();
            Request.Builder requestBuilder = request.newBuilder().url(httpUrl);
            return chain.proceed(requestBuilder.build());
        });


        return httpClientBuilder.build();
    }

    @NonNull
    private static Gson makeGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
