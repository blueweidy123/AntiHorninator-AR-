package com.blueweidy.myapplication;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.blueweidy.myapplication.Adapter.Frag2_NoteAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class Frag2_RepeatMode extends Fragment implements note_update{

    private AlarmManager alarmManager;

    private Frag2_NoteAdapter adapter;
    ArrayList<Frag3_Alarm> alarmList;

    Database_sqlite database;
    private int h, m;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database_sqlite(getActivity());
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag4, container, false);
        //alarm - fea
        //final View view = inflater.inflate(R.layout.fragment_frag2__repeat_mode, container, false);
        /*alarmList = new ArrayList<>();

        RecyclerView rcvNotes = view.findViewById(R.id.rcv_notes);
        Button addAlarm = view.findViewById(R.id.add_alarm_button);
        addAlarm.setOnClickListener(v -> addAlarm());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvNotes.setLayoutManager(linearLayoutManager);
        rcvNotes.setFocusable(false);

        adapter = new Frag2_NoteAdapter(getActivity(),R.layout.frag2_single_row_timetable, alarmList, this );
        rcvNotes.setAdapter(adapter);
        GetData();*/
        return view;

    }

    private void GetData(){
        alarmList.clear();
        try {
            alarmList.addAll(database.getAllAlarmNote());
        }catch (NullPointerException e){
            database.createalarmTable();
            alarmList.addAll(database.getAllAlarmNote());
        }
        adapter.notifyDataSetChanged();
    }

    private void getActiveAlarm(){
        for (int i = 0; i < alarmList.size(); i++){
            Frag3_Alarm alarm = alarmList.get(i);
            SETALARM(alarm);
        }
    }

    private void SETALARM(Frag3_Alarm alarm){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getTime_of_day());
        calendar.set(Calendar.MINUTE, alarm.getNote_minute());

        STARTALARM(calendar, alarm);
    }

    private void STARTALARM(Calendar calendar, Frag3_Alarm alarm){
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), alarm.getnID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        if (calendar.after(now)){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void addAlarm(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_alarm);
        final EditText editN = dialog.findViewById(R.id.edittextALARM);
        final TextView picktime = dialog.findViewById(R.id.addAlarm_timepick);
        picktime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getActivity(),
                    (view, hourOfDay, minute) -> {
                        h = hourOfDay;
                        m = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0, h, m);
                        picktime.setText(DateFormat.format("hh:mm aa", calendar));
                        getActiveAlarm();
                    },12,0,false
            );
            timePickerDialog.updateTime(h, m);
            timePickerDialog.show();
        });

        Button addBttn = dialog.findViewById(R.id.alarmaddBttnDialog);
        addBttn.setOnClickListener(v -> {
            String name = editN.getText().toString();
            if (name.equals("")){
                database.insertAlarm("null", 00, 00, 0, 1,1,1,1,1,1,1, 1);
                dialog.dismiss();
                GetData();
                getActiveAlarm();
            }else {
                database.insertAlarm(name, h, m, 0,1,1,1,1,1,1,1, 1);
                dialog.dismiss();
                GetData();
                getActiveAlarm();
            }
        });
        Button cancelBttn = dialog.findViewById(R.id.alarmcancelBttn);
        cancelBttn.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void updateNoteName(String name, final int id, boolean update) {
        if (update){
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_change_alarm);
            final EditText edt = dialog.findViewById(R.id.edittext_alarm_change);
            Button cf = dialog.findViewById(R.id.Confirm_alarm_c);
            edt.setText(name);
            cf.setOnClickListener(v -> {
                String aName = edt.getText().toString();
                database.updateAlarmName(aName, id);
                dialog.dismiss();
                GetData();
                getActiveAlarm();
            });
            Button dismiss = dialog.findViewById(R.id.cancel_alarm_change);
            dismiss.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        }
    }

    @Override
    public void updateTime(int hh, int mm, int id, boolean update) {
        if (update){
            database.updateAlarmTime(id, hh, mm);
            GetData();
            getActiveAlarm();
        }
    }

    @Override
    public void delete(final String name, final int id, boolean update) {
        if (update){
            AlertDialog.Builder del = new AlertDialog.Builder(getActivity());
            del.setMessage("Delete "+name+" ?");
            del.setPositiveButton("Yes", (dialog, which) -> {
                database.deleteAlarm(id);
                Toast.makeText(getActivity(), "Deleted "+name, Toast.LENGTH_SHORT).show();
                GetData();
                getActiveAlarm();
            });
            del.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            del.show();
        }
    }

    @Override
    public void repeat(int id, int Repeatboolean, boolean update) {
        if (update){
            database.updateAlarmRepeat(id, Repeatboolean);
            getActiveAlarm();
        }
    }

    @Override
    public void sundaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateAlarmSun(id, daySet);
        }
    }

    @Override
    public void mondaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateAlarmMon(id, daySet);
        }
    }

    @Override
    public void tuedaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateAlarmTue(id, daySet);
        }
    }

    @Override
    public void wednesdaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateAlarmWed(id, daySet);
        }
    }

    @Override
    public void thursdaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateAlarmThu(id, daySet);
        }
    }

    @Override
    public void fridaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateAlarmFri(id, daySet);
        }
    }

    @Override
    public void saturdaySet(int id, int daySet, boolean update) {
        if (update){
            database.updateAlarmSat(id, daySet);
        }
    }

    @Override
    public void turn(int id, int switchboolean, boolean update) {
        if (update){
            database.updateState(id, switchboolean);
            getActiveAlarm();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //trigger crash on destroy
        //getActiveAlarm();
    }
}
