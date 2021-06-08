package com.blueweidy.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class RingtoneService extends Service {

    Ringtone ringtone;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone(getBaseContext(), alarmUri);
        if (ringtone == null){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtone = RingtoneManager.getRingtone(getBaseContext(), alarmUri);
            if (ringtone == null){
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                ringtone = RingtoneManager.getRingtone(getBaseContext(), alarmUri);
            }
        }
        if (ringtone != null){
            ringtone.play();
        }
        return START_STICKY;
}

    private void dismissRingtone() {
        Intent intent = new Intent(this, RingtoneService.class);
        stopService(intent);
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
    }
}
