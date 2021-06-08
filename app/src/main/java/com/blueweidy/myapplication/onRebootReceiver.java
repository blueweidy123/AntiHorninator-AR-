package com.blueweidy.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;

public class onRebootReceiver extends BroadcastReceiver {

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Database_sqlite database_sqlite;
    private ArrayList<Frag2_Notes> noteL;
    private ArrayList<Frag3_Alarm> alarmL;

    @Override
    public void onReceive(Context context, Intent intent) {
        database_sqlite = new Database_sqlite(context);
        noteL = (ArrayList<Frag2_Notes>) database_sqlite.getAllNote();
        alarmL = (ArrayList<Frag3_Alarm>) database_sqlite.getAllAlarmNote();

        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            getActiveAlarm(context);
            getActiveNote(context);
        }
    }


    public void getActiveAlarm(Context context){
        getAlarm();
        for (int i = 0; i < alarmL.size(); i++){
            Frag3_Alarm alarm = alarmL.get(i);
            SETALARM(alarm, context);
        }
    }

    public void getActiveNote(Context context){
        getNote();
        for (int i = 0; i < noteL.size(); i++){
            Frag2_Notes notes = noteL.get(i);
            SETNOTE(notes, context);
        }
    }

    //region alarm
    public void SETALARM(Frag3_Alarm alarm, Context context){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getTime_of_day());
        calendar.set(Calendar.MINUTE, alarm.getNote_minute());

        STARTALARM(calendar, alarm, context);
    }

    private void STARTALARM(Calendar calendar, Frag3_Alarm alarm, Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("command", 1);
        intent.putExtra("id", alarm.getnID());
        intent.putExtra("title", alarm.getnName());
        intent.putExtra("state", alarm.getstate());
        intent.putExtra("repeat", alarm.getRepeat());
        intent.putExtra("hour", alarm.getTime_of_day());
        intent.putExtra("min", alarm.getNote_minute());
        intent.putExtra("mon", alarm.getMon());
        intent.putExtra("tue", alarm.getTue());
        intent.putExtra("wed", alarm.getWed());
        intent.putExtra("thu", alarm.getThu());
        intent.putExtra("fri", alarm.getFri());
        intent.putExtra("sat", alarm.getSat());
        intent.putExtra("sun", alarm.getSun());
        pendingIntent = PendingIntent.getBroadcast(context, alarm.getnID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        if (calendar.after(now)){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }else {
            calendar.add(Calendar.DATE, 1);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
    //endregion

    //region note
    public void SETNOTE(Frag2_Notes notes, Context context){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, notes.getTime_of_day());
        calendar.set(Calendar.MINUTE, notes.getNote_minute());

        STARTNOTE(calendar, notes, context);
    }

    private void STARTNOTE(Calendar calendar, Frag2_Notes alarm, Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("command", 0);
        intent.putExtra("id", alarm.getnID());
        intent.putExtra("title", alarm.getnName());
        intent.putExtra("repeat", alarm.getRepeat());
        intent.putExtra("hour", alarm.getTime_of_day());
        intent.putExtra("min", alarm.getNote_minute());
        intent.putExtra("mon", alarm.getMon());
        intent.putExtra("tue", alarm.getTue());
        intent.putExtra("wed", alarm.getWed());
        intent.putExtra("thu", alarm.getThu());
        intent.putExtra("fri", alarm.getFri());
        intent.putExtra("sat", alarm.getSat());
        intent.putExtra("sun", alarm.getSun());
        pendingIntent = PendingIntent.getBroadcast(context, alarm.getnID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        if (calendar.after(now)){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }else {
            calendar.add(Calendar.DATE, 1);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
    //endregion

    public void getAlarm(){
        alarmL.clear();
        alarmL.addAll(database_sqlite.getAllAlarmNote());
    }

    public void getNote(){
        noteL.clear();
        noteL.addAll(database_sqlite.getAllNote());
    }
}
