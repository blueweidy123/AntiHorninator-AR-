package com.blueweidy.myapplication;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class BroadcastService extends Service {

    public static final String
            ACTION_TIME_BROADCAST = BroadcastService.class.getName() + "TimeBroadcast",
            TIME = "extra_time";



    Timer timer;
    TimerTask timerTask;
    double time = 0.0;

    boolean timerstarted = false;

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(getApplicationContext(), "started", Toast.LENGTH_SHORT).show();


        timer = new Timer();
        start();
    }

    private void sendBroadcastMessage(String time){
        Intent intent = new Intent(ACTION_TIME_BROADCAST);
        intent.putExtra(TIME, getTimerText());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void reset(){
        if (timerTask != null){
            timerTask.cancel();
            time = 0.0;
            timerstarted = false;
            //settext
        }
    }

    public void start(){
        if (timerstarted == false){
            timerstarted = true;
            startTimer();
        }else {
            timerstarted = false;
            timerTask.cancel();
        }
    }

    private void startTimer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                time++;
                Intent intent = new Intent(getApplicationContext(), Fragment4.class);
                intent.putExtra("time", time);
                sendBroadcastMessage(getTimerText());
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timerTask.cancel();
    }
}
