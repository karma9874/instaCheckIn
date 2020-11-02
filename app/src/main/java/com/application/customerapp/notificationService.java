package com.application.customerapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class notificationService  extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            System.out.println("Message Data : " + remoteMessage.getData());
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        }
    }

    private void showNotification(String title, String message) {

        Intent intent = new Intent(this, splashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"MyNotifcation")
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.smallicon)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setDefaults(Notification.DEFAULT_ALL)
                ;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel("MyNotifcation", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            assert notificationManager != null;
            builder.setChannelId("MyNotifcation");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(123,builder.build());
    }

    @Override
    public void onNewToken(String s) {
        Log.d("test",s);
        super.onNewToken(s);
    }


}
