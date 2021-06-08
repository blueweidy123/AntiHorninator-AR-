package com.blueweidy.myapplication;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NofificationHelper extends ContextWrapper {

    private static final String channelID = "MCN";
    public final String channelName = "AntiHornimator";
    static  final int NOTI_ID= 1;
    public String text;
    public int id;
    private NotificationManager notificationManager;

    public NofificationHelper(Context base, String text) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel(base);
        }
        this.text = text;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

            if (managerCompat.getNotificationChannel(channelID) == null){
                NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("ARchannel");
                managerCompat.createNotificationChannel(channel);
            }
        }
    }

    public NotificationManager getManager(){
        if (notificationManager == null){
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification(){
        Intent notiIntent = new Intent(this, MainActivity.class);
        notiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notiPending = PendingIntent.getActivity(this, id, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentIntent(notiPending)
                .setContentTitle(text)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentText("Học đi mà làm người bạn êyy!!")
                .setSmallIcon(R.drawable.ic_school_black_24dp)
                .setAutoCancel(true);
    }

    public static void CreateFullScreenNoti(Context context){
        Intent intent = new Intent(context, fullScreenActvity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_NO_USER_ACTION | intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(context, channelID )
                .setSmallIcon(R.drawable.ic_school_black_24dp)
                .setContentTitle("fullscreentest")
                .setContentText("test")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setFullScreenIntent(pendingIntent, true);
        NotificationManagerCompat.from(context).notify(NOTI_ID, noBuilder.build());
    }



    public NotificationCompat.Builder getChannelNoti(){
        Intent notiIntent = new Intent(this, MainActivity.class);
        notiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent notiPending = PendingIntent.getActivity(this, id, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(this, RingtoneService.class);
        PendingIntent snoozePI = PendingIntent.getBroadcast(this, id, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action = new NotificationCompat.Action(R.mipmap.ic_launcher, "SNOOZE", snoozePI);

        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setSmallIcon(R.drawable.ic_school_black_24dp)
                .setContentIntent(notiPending)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setFullScreenIntent(notiPending, true)
                .setContentTitle(text)
                .setContentText("Chơi cho biết thôi chứ nghiện là dở rồi :))")
                .addAction(action)
                .setAutoCancel(true);
    }
}
