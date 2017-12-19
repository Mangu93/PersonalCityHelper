package com.mangu.personalcityhelper.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import com.mangu.personalcityhelper.R;
import com.mangu.personalcityhelper.ui.weather.WeatherActivity;

public class NotificationUtils {
    public static final String NOTIFICATION_CHANNEL_ID = "4655";

    public static void createWeatherNotification(Context context) {

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence channelName = "Tu Rincon de la Victoria";
        String content = context.getString(R.string.check_weather);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            Notification.Builder mBuilder = new Notification.Builder(context,
                    NOTIFICATION_CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(content)
                    .setContentText(content);
            Intent mIntent = new Intent(context, WeatherActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setDescription("Test Description");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300,
                    400, 500, 400, 300, 200, 400});
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
                Notification notification = mBuilder.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(4655, notification);
            }
        } else {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(content)
                    .setContentText(content);
            Intent mIntent = new Intent(context, WeatherActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);
            Notification notification = mBuilder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            if (notificationManager != null) {
                notificationManager.notify(4655, notification);
            }
        }


    }
}
