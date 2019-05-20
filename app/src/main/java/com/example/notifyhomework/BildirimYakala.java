package com.example.notifyhomework;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

public class BildirimYakala extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager bildirimYonet = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification bildirim = intent.getParcelableExtra("nesne");

        bildirimYonet.notify(1,bildirim);


    }
}
