package com.example.notifyhomework;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Button buttonBildir;
    private String GROUP_KEY = "com.android.example.WORK_EMAIL";
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private NotificationCompat.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager bildirimYonet =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent ıntent = new Intent(MainActivity.this,KarsilamaEkraniActivity.class);

        PendingIntent gidilecekIndent = PendingIntent.getActivity(this,1,ıntent,PendingIntent.FLAG_UPDATE_CURRENT);

        // Oreo Sürüm Kontrolü
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String kanalId="kanalId";
            String kanalAd="kanalAd";
            String kanalTanım="kanalTanım";
            int kanalOncelgi=NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel kanal = bildirimYonet.getNotificationChannel(kanalId);

            if (kanal == null) {

                kanal = new NotificationChannel(kanalId,kanalAd,kanalOncelgi);
                kanal.setDescription(kanalTanım);
                bildirimYonet.createNotificationChannel(kanal);
            }

            builder = new NotificationCompat.Builder(this,kanalId);

            builder.setContentTitle("Mola vakti");
            builder.setContentText("Dersine biraz ara verebilirsin..");
            builder.setSmallIcon(R.drawable.resim);
            builder.setAutoCancel(true);
            builder.setContentIntent(gidilecekIndent);
            builder.setGroup(GROUP_KEY);
            builder.build();

        } else {
            builder = new NotificationCompat.Builder(this);

            builder.setContentTitle("Mola Vakti");
            builder.setContentText("Dersine biraz ara verebilirsin..");
            builder.setSmallIcon(R.drawable.resim);
            builder.setAutoCancel(true);
            builder.setContentIntent(gidilecekIndent);
            builder.setPriority(Notification.PRIORITY_HIGH);
            builder.setGroup(GROUP_KEY);
            builder.build();

        }

        Intent broadcastIntent = new Intent(MainActivity.this, BildirimYakala.class);

        broadcastIntent.putExtra("nesne",builder.build());

        PendingIntent gidilecekBroadcast = PendingIntent.getBroadcast(this,
                0,
                broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //long gecikme = SystemClock.elapsedRealtime() + 60000;

        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(System.currentTimeMillis());
        calender.set(Calendar.HOUR_OF_DAY,14);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),900000 ,gidilecekBroadcast);

    }

}
