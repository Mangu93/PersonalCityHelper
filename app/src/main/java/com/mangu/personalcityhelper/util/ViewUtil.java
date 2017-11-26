package com.mangu.personalcityhelper.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.data.local.BusItem;
import com.mangu.personalcityhelper.data.model.BeachPrediction;
import com.mangu.personalcityhelper.data.model.BeachPredictionUtil;
import com.mangu.personalcityhelper.data.model.bus.Line;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

import static com.mangu.personalcityhelper.data.model.BeachPredictionUtil.preparePrediction;
import static com.mangu.personalcityhelper.util.NetworkUtil.extractImgUrl;
import static com.mangu.personalcityhelper.util.StringUtil.beachStrings;
import static com.mangu.personalcityhelper.util.StringUtil.formatCapitalized;

public class ViewUtil {
    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static List<LinearLayout> processDocumentIntoLayout(Context context, Document document) {
        List<LinearLayout> layoutList = new ArrayList<>();
        Elements elements = document.getElementsByClass("estado-2");
        for (Element elementNew : elements) {
            LinearLayout linearLayout = prepareLayout(context, elementNew);
            if (linearLayout == null) {
                continue;
            }
            layoutList.add(linearLayout);
            Log.i("TAG", elementNew.text() + "\n");
        }
        return layoutList;
    }

    public static List<LinearLayout> generateErrorLayout(Context context) {
        List<LinearLayout> layoutList = new ArrayList<>();
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setPadding(0, 0, 0, dpToPx(10));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(context);
        textView.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(context.getText(R.string.error_no_connection));
        textView.setPadding(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        textView.setTypeface(null, Typeface.BOLD_ITALIC);
        linearLayout.addView(textView);
        layoutList.add(linearLayout);
        return layoutList;
    }

    public static LinearLayout prepareBusLayout(Context context, Document document) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Elements elements = document.getElementsByClass("WYSIWYG");
        for (Element newElement : elements.get(0).children()) {
            Log.i("TAG", newElement.toString());
        }
        return linearLayout;
    }


    @NonNull
    public static BusItem generateBusItem(Line line) {
        return new BusItem(line.getCodigo(),
                line.getNombre() + ", via " + line.getModo() + " " +
                        line.getOperadores().replace(",", ""), line.getIdLinea());
    }

    @Nullable
    private static LinearLayout prepareLayout(Context context, Element elementNew) {
        String headline = elementNew.getAllElements().select(".tr02s").text();
        String linkUrl = "http://www.rincondelavictoria.es/"
                + elementNew.getAllElements().select(".imagen.row").attr("href");
        String extractedUrlImg =
                elementNew.getAllElements().select(".imagen.row").attr("style");
        String imgUrl = extractImgUrl(extractedUrlImg);
        if (headline.equalsIgnoreCase("") || linkUrl.equalsIgnoreCase("")) {
            return null;
        }
        return createNewsLayout(context, linkUrl, headline, imgUrl);
    }

    private static LinearLayout createNewsLayout(Context context,
                                                 String url, String headline, String imgUrl) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setPadding(0, 0, 0, dpToPx(10));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView headlineTextView = generateHeadline(context, headline);
        headlineTextView.setOnClickListener(view ->
                generateWebPopup(context, url).showAtLocation(view, Gravity.CENTER, 0, 0));

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(400, 300));
        imageView.setOnClickListener(view ->
                generateWebPopup(context, url).showAtLocation(view, Gravity.CENTER, 0, 0));
        Glide.with(context)
                .load(imgUrl)
                .bitmapTransform(new CropSquareTransformation(context))
                .placeholder(R.drawable.ic_news)
                .into(imageView);


        linearLayout.addView(imageView);
        linearLayout.addView(headlineTextView);
        return linearLayout;
    }

    private static TextView generateHeadline(Context context, String headline) {
        TextView headlineTextview = new TextView(context);
        headlineTextview.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        headlineTextview.setText(headline);
        headlineTextview.setTextColor(Color.BLACK);
        headlineTextview.setTypeface(null, Typeface.BOLD_ITALIC);
        return headlineTextview;
    }

    @NonNull
    public static Snackbar createErrorSnackbar(Context context, int idLayout) {
        return Snackbar.make(((Activity) context).findViewById(idLayout),
                "Sorry, try again, there was an error with the connection", Snackbar.LENGTH_LONG);
    }

    @NonNull
    public static Snackbar createErrorSnackbar(View view) {
        return Snackbar.make(view,
                "Sorry, try again, there was an error with the connection", Snackbar.LENGTH_LONG);
    }


    @SuppressLint("SetJavaScriptEnabled")
    private static PopupWindow generateWebPopup(Context context, String linkUrl) {
        @SuppressLint("InflateParams") View popUpView = ((Activity) context).getLayoutInflater().
                inflate(R.layout.view_web, null);
        PopupWindow popupWindow = new PopupWindow(popUpView,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        final WebView webView = (WebView) popUpView.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //noinspection EmptyMethod
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "var target = document.getElementsByClassName('" +
                        "rincondelavictoria-_basico-index-twml')[0];" +
                        "var target_offset = target.offsetTop;" +
                        "$('html,body').animate({scrollTop: target_offset}, 1000);" +
                        "})()");
            }

            @SuppressWarnings("EmptyMethod")
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        webView.loadUrl(linkUrl);

        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return popupWindow;
    }

    @SuppressWarnings("deprecation")
    public static View generateBeachTextView(JSONObject jsonObject, Context context)
            throws JSONException {
        String[] stringsForPrediction = beachStrings(context);
        BeachPrediction prediction = new BeachPrediction(stringsForPrediction[0],
                stringsForPrediction[1], stringsForPrediction[2], stringsForPrediction[3],
                stringsForPrediction[4], stringsForPrediction[5], stringsForPrediction[6],
                stringsForPrediction[7], stringsForPrediction[8], stringsForPrediction[9]);
        prediction = preparePrediction(jsonObject, prediction);

        @SuppressLint("InflateParams") View view = ((Activity) context).getLayoutInflater().
                inflate(R.layout.view_beach, null);
        final TextView textView = (TextView) view.findViewById(R.id.tv_beach);
        final ImageView imageView = (ImageView) view.findViewById(R.id.beach_icon);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(prediction.presentation(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(prediction.presentation()));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setElevation(3f);
        }
        try {
            Glide.with(context)
                    .load(Integer.parseInt(
                            BeachPredictionUtil.getIconPrediction(prediction)))
                    .bitmapTransform(new CropCircleTransformation(context))
                    .placeholder(R.drawable.ic_sunny)
                    .into(imageView);
        } catch (NumberFormatException ex) {
            Glide.with(context)
                    .load(BeachPredictionUtil.getIconPrediction(prediction))
                    .bitmapTransform(new CropCircleTransformation(context))
                    .placeholder(R.drawable.ic_sunny)
                    .into(imageView);
        }
        return view;
    }

    public static LinearLayout createPharmacyLayout(Context context, Document data) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setPadding(0, 0, 0, dpToPx(10));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        StringBuilder message = new StringBuilder(data.body().getElementById("marco-aux").getAllElements().get(0).text());
        message = new StringBuilder(message.toString().replace("RICON", "RINCON"));
        String[] messageSplitted = message.toString().split("\\s*(?=G)|(?=RINCON)");
        message = new StringBuilder(context.getString(R.string.emergency_pharmacy));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            message.append(String.join("\n", messageSplitted));
        } else {
            for (String msg : messageSplitted) {
                message.append("\n").append(msg);
            }
        }
        TextView textView = new TextView(context);
        textView.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(message.toString());
        textView.setPadding(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        textView.setTypeface(null, Typeface.BOLD_ITALIC);
        linearLayout.addView(textView);
        return linearLayout;
    }

    public static void createTablePhone(Context context, String[] telephones,
                                        TableLayout tableLayout, View.OnClickListener listener) {
        for (String telephone : telephones) {
            String tlf = telephone.replaceAll("\\s+", "");
            //The place is in the first position of the splitted string
            String place = tlf.split("([0-9]+)")[0];
            //The number is in the second position of this regex
            String[] splitted = tlf.split("([a-zA-ZÀ-ÿ]+)");
            String number = "";
            if (1 <= splitted.length) {
                number = splitted[1];
            }
            TableRow tableRow = new TableRow(context);
            TextView left = new TextView(context);
            left.setLayoutParams(new TableRow.LayoutParams(1));
            left.setText(formatCapitalized(place));
            left.setGravity(Gravity.END);
            left.setPadding(0, 0, dpToPx(5), dpToPx(5));
            if (number.equalsIgnoreCase("")) {
                left.setTypeface(null, Typeface.BOLD);
                SpannableString spannableString = new SpannableString(formatCapitalized(place));
                spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
                left.setText(spannableString);
            }
            TextView right = new TextView(context);
            right.setText(number);
            right.setPadding(dpToPx(5), 0, 0, dpToPx(5));
            right.setOnClickListener(listener);
            tableRow.addView(left);
            tableRow.addView(right);
            tableLayout.addView(tableRow);
        }
    }

}
