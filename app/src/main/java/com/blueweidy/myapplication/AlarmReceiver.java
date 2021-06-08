package com.blueweidy.myapplication;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import androidx.core.app.NotificationCompat;
import java.util.Calendar;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int command  = intent.getIntExtra("command", 0);//default a noti
        int id       = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("title");
        int repeat   = intent.getIntExtra("repeat", 0);
        int mon      = intent.getIntExtra("mon", 0);
        int tue      = intent.getIntExtra("tue", 0);
        int wed      = intent.getIntExtra("wed", 0);
        int thu      = intent.getIntExtra("thu", 0);
        int fri      = intent.getIntExtra("fri", 0);
        int sat      = intent.getIntExtra("sat", 0);
        int sun      = intent.getIntExtra("sun", 0);

        int state    = intent.getIntExtra("state", 1);
        if (command == 0){
            if (repeat == 0) {
                /*if (daySetR(mon, tue, wed, thu, fri, sat, sun)){*/
                    showNoteNoti(context, title, id);
                    startActiv(context);
                /*}*/
            }else if (repeat == 1){
                showNoteNoti(context, title, id);
                startActiv(context);
            }
        }else if (command == 1){
            if (state == 1){
                if (repeat == 0){
                    if (daySetR(mon, tue, wed, thu, fri, sat, sun)){
                        startAlarm(context, id, title);
                    }
                }else if (repeat == 1){
                    startAlarm(context, id, title);
                }
            }
        }
    }

    private boolean daySetR(int mon, int tue, int wed, int thu, int fri, int sat, int sun){
        int DoW = Calendar.getInstance().DAY_OF_WEEK;
        switch (DoW){
            case 1:{
                if (mon == 1){
                    return true;
                }
            }
            case 2:{
                if (tue == 1){
                    return true;
                }
            }
            case 3:{
                if (wed == 1){
                    return true;
                }
            }
            case 4:{
                if (thu == 1){
                    return true;
                }
            }
            case 5:{
                if (fri == 1){
                    return true;
                }
            }
            case 6:{
                if (sat == 1){
                    return true;
                }
            }
            case 7:{
                if (sun == 1){
                    return true;
                }
            }
        }
        return false;
    }

    private void startAlarm(Context context,int id, String title) {
        NofificationHelper nofificationHelper = new NofificationHelper(context, title);
        NotificationCompat.Builder notiBuilder = nofificationHelper.getChannelNoti();
        nofificationHelper.getManager().notify(id, notiBuilder.build());
        ringtone(context);
    }

    private void ringtone(Context context){
        Intent intent = new Intent(context, RingtoneService.class);
        context.startService(intent);
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(10000);
    }

    public void showNoteNoti(final Context context, String text, int id){
        NofificationHelper nofificationHelper = new NofificationHelper(context, text);
        NotificationCompat.Builder nb = nofificationHelper.getChannelNotification();
        nofificationHelper.getManager().notify(id, nb.build());
    }

    public static boolean isAppForeground(final Context context){
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> runningProgress = activityManager.getRunningAppProcesses();
        for (final ActivityManager.RunningAppProcessInfo processInfo : runningProgress){
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                for (final String activeProgress : processInfo.pkgList){
                    if (activeProgress.equals(context.getPackageName())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void startActiv(Context context){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (!isAppForeground(context)){
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                try{
                    pendingIntent.send();
                }catch (PendingIntent.CanceledException e){
                    e.printStackTrace();
                }
            }
        }, 5000);

    }
}
