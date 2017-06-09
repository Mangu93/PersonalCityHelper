package com.mangu.personalcityhelper.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.HttpException;

public class NetworkUtil {
    public static boolean isHttpStatusCode(Throwable throwable, int statusCode) {
        return throwable instanceof HttpException
                && ((HttpException) throwable).code() == statusCode;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static String extractImgUrl(String imgUrl) {
        Pattern pattern = Pattern.compile("http(s?):\\/{2}(www)?(.)*\\.(jpeg|png|jpg)",
                Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(imgUrl);
        List<String> containedUrls = new ArrayList<>();
        while (urlMatcher.find()) {
            containedUrls.add(imgUrl.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }
        if (containedUrls.size() > 0) {
            return containedUrls.get(0);
        } else {
            return "";
        }
    }

    public static String getURLContent(String p_sURL) {
        URL oURL;
        URLConnection oConnection;
        BufferedReader oReader;
        String sLine;
        StringBuilder sbResponse;
        String sResponse = null;

        try {
            oURL = new URL(p_sURL);
            oConnection = oURL.openConnection();
            oReader = new BufferedReader(new InputStreamReader(oConnection.getInputStream(), "ISO_8859_1"));
            sbResponse = new StringBuilder();

            while ((sLine = oReader.readLine()) != null) {
                sbResponse.append(sLine);
            }

            sResponse = sbResponse.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sResponse;
    }
}
