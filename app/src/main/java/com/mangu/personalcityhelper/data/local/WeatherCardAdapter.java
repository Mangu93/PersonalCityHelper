package com.mangu.personalcityhelper.data.local;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.mangu.personalcityhelper.R;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.mangu.personalcityhelper.util.StringUtil.formatTimestamp;
import static com.mangu.personalcityhelper.util.StringUtil.getMinAndMax;
import static com.mangu.personalcityhelper.util.StringUtil.kelvinToCelsius;

@SuppressWarnings("CanBeFinal")
public class WeatherCardAdapter extends
        RecyclerView.Adapter<WeatherCardAdapter.ViewHolder> implements View.OnClickListener {
    private static final String BASE_PIC_URL = "http://openweathermap.org/img/w/";

    private Context mContext;
    private List<JsonObject> mJson;
    private View.OnClickListener mClickListener;

    public WeatherCardAdapter(Context context) {
        this.mContext = context;
    }

    public List<JsonObject> getJson() {
        return mJson;
    }

    public void setJson(List<JsonObject> listJson) {
        this.mJson = listJson;
        this.notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JsonObject jsonObject = mJson.get(position);
        JsonObject main = jsonObject.getAsJsonObject("main");
        int timestamp = jsonObject.get("dt").getAsInt();
        JsonObject weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
        holder.mTemperature.append(" " +
                kelvinToCelsius(main.get("temp").toString()) + "ºC");
        holder.mMinAndMax.append(" " + getMinAndMax(main));
        holder.mPressure.append(" " + main.get("humidity").toString() + "%");
        String iconUrl = weather.get("icon").toString();
        iconUrl = iconUrl.replaceAll("\"", "");
        holder.mDay.append(formatTimestamp(timestamp));
        Glide.with(mContext)
                .load(BASE_PIC_URL + iconUrl + ".png")
                .bitmapTransform(new CropCircleTransformation(mContext))
                .placeholder(R.drawable.ic_sunny)
                .into(holder.mImageWeather);
    }


    @Override
    public int getItemCount() {
        if (this.mJson != null) {
            return mJson.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onClick(View view) {
        if (mClickListener != null) {
            mClickListener.onClick(view);
        }
    }


    @SuppressWarnings("CanBeFinal")
    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView mTemperature;
        TextView mPressure;
        TextView mMinAndMax;
        TextView mDay;
        ImageView mImageWeather;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            mTemperature = (TextView) itemView.findViewById(R.id.tv_temperature);
            mPressure = (TextView) itemView.findViewById(R.id.tv_pressure);
            mMinAndMax = (TextView) itemView.findViewById(R.id.tv_maxim);
            mImageWeather = (ImageView) itemView.findViewById(R.id.weather_photo);
            mDay = (TextView) itemView.findViewById(R.id.tv_day);
        }
    }
}
