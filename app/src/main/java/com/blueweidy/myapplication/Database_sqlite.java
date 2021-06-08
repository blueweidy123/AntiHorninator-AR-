package com.blueweidy.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database_sqlite extends SQLiteOpenHelper {

    //region init
    private static final String DATABASE_NAME = "Notes_data";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTE = "notes";
    private static final String TABLE_ALARM = "alarms";
    //endregion

    public Database_sqlite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTable = "CREATE TABLE IF NOT EXISTS "+ TABLE_NOTE + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " notename VARCHAR(255)," +
                " timeOfH INTEGER NOT NULL," +
                " min INTEGER NOT NULL," +
                " repeat INTEGER NOT NULL," +
                " mon INTEGER NOT NULL," +
                " tue INTEGER NOT NULL," +
                " wed INTEGER NOT NULL," +
                " thu INTEGER NOT NULL," +
                " fri INTEGER NOT NULL," +
                " sat INTEGER NOT NULL," +
                " sun INTEGER NOT NULL)";

        String _CreateTable = "CREATE TABLE IF NOT EXISTS "+ TABLE_ALARM + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " alarmname VARCHAR(255)," +
                " timeOfH INTEGER NOT NULL," +
                " min INTEGER NOT NULL," +
                " repeat INTEGER NOT NULL," +
                " mon INTEGER NOT NULL," +
                " tue INTEGER NOT NULL," +
                " wed INTEGER NOT NULL," +
                " thu INTEGER NOT NULL," +
                " fri INTEGER NOT NULL," +
                " sat INTEGER NOT NULL," +
                " sun INTEGER NOT NULL," +
                " state INTEGER NOT NULL)";

        db.execSQL(CreateTable);
        db.execSQL(_CreateTable);
    }

    public void createalarmTable(){
        SQLiteDatabase database = getWritableDatabase();
        String _CreateTable = "CREATE TABLE IF NOT EXISTS "+ TABLE_ALARM + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " alarmname VARCHAR(255)," +
                " timeOfH INTEGER NOT NULL," +
                " min INTEGER NOT NULL," +
                " repeat INTEGER NOT NULL," +
                " mon INTEGER NOT NULL," +
                " tue INTEGER NOT NULL," +
                " wed INTEGER NOT NULL," +
                " thu INTEGER NOT NULL," +
                " fri INTEGER NOT NULL," +
                " sat INTEGER NOT NULL," +
                " sun INTEGER NOT NULL," +
                " state INTEGER NOT NULL)";
        database.execSQL(_CreateTable);
    }

    public void deleteTable(int getTable){
        SQLiteDatabase database = getWritableDatabase();
        if (getTable == 0){
            database.execSQL("DROP TABLE IF EXISTS "+ TABLE_NOTE);
        }else if (getTable == 1){
            database.execSQL("DROP TABLE IF EXISTS "+ TABLE_ALARM);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ALARM);
        onCreate(db);
    }


    public List<Frag2_Notes> getAllNote(){
        List<Frag2_Notes> notesList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NOTE+" ORDER BY timeOfH ASC, min ASC", null);
        while (cursor.moveToNext()){
            int id           = cursor.getInt(0);
            String note_name = cursor.getString(1);
            int TimeH        = cursor.getInt(2);
            int Min          = cursor.getInt(3);
            int repeat       = cursor.getInt(4);
            int mon          = cursor.getInt(5);
            int tue          = cursor.getInt(6);
            int wed          = cursor.getInt(7);
            int thu          = cursor.getInt(8);
            int fri          = cursor.getInt(9);
            int sat          = cursor.getInt(10);
            int sun          = cursor.getInt(11);
            notesList.add(new Frag2_Notes(id, note_name, TimeH, Min, repeat, mon, tue, wed, thu, fri, sat, sun));
        }
        cursor.close();
        return notesList;
    }

    public List<Frag3_Alarm> getAllAlarmNote(){
        List<Frag3_Alarm> alarmList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_ALARM+" ORDER BY timeOfH ASC, min ASC", null);
        while (cursor.moveToNext()){
            int id           = cursor.getInt(0);
            String note_name = cursor.getString(1);
            int TimeH        = cursor.getInt(2);
            int Min          = cursor.getInt(3);
            int repeat       = cursor.getInt(4);
            int mon          = cursor.getInt(5);
            int tue          = cursor.getInt(6);
            int wed          = cursor.getInt(7);
            int thu          = cursor.getInt(8);
            int fri          = cursor.getInt(9);
            int sat          = cursor.getInt(10);
            int sun          = cursor.getInt(11);
            int state        = cursor.getInt(12);
            alarmList.add(new Frag3_Alarm(id, note_name, TimeH, Min, repeat, mon, tue, wed, thu, fri, sat, sun, state));
        }
        cursor.close();
        return alarmList;
    }

    void updateName(String name, int id){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_NOTE+" SET notename = '"+name+"' WHERE Id = '"+id+"'");
    }

    void updateAlarmName(String name, int id){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_ALARM+" SET alarmname = '"+name+"' WHERE Id = '"+id+"'");
    }

    void insert(String name,int hour, int min, int repeat, int mon, int tue, int wed, int thu, int fri, int sat, int sun){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO "+TABLE_NOTE+" VALUES(null,'"+name+"', "+hour+", "+min+", "+repeat+", "+mon+", " +
                ""+tue+", "+wed+", "+thu+", "+fri+", "+sat+", "+sun+")");
    }

    void insertAlarm(String name,int hour, int min, int repeat, int mon, int tue, int wed, int thu, int fri, int sat, int sun, int state){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("INSERT INTO "+TABLE_ALARM+" VALUES(null,'"+name+"', "+hour+", "+min+", "+repeat+", "+mon+", " +
                ""+tue+", "+wed+", "+thu+", "+fri+", "+sat+", "+sun+", "+state+")");
    }

    void updateState(int id, int bool){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_ALARM+" SET state = '"+bool+"' WHERE Id = '"+id+"'");
    }

    void delete(int id){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM "+TABLE_NOTE+" WHERE Id = '"+ id +"'");
    }

    void deleteAlarm(int id){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM "+TABLE_ALARM+" WHERE Id = '"+ id +"'");
    }

    void updateTime(int id, int hour, int min){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_NOTE+" SET timeOfH = '"+hour+"' WHERE Id = '"+id+"'");
        database.execSQL("UPDATE "+TABLE_NOTE+" SET min     = '"+min+"' WHERE Id = '"+id+"'");
    }

    void updateAlarmTime(int id, int hour, int min){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_ALARM+" SET timeOfH = '"+hour+"' WHERE Id = '"+id+"'");
        database.execSQL("UPDATE "+TABLE_ALARM+" SET min     = '"+min+"' WHERE Id = '"+id+"'");
    }

    void updateRepeat(int id, int bool){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_NOTE+" SET repeat = '"+bool+"' WHERE Id = '"+id+"'");
    }

    void updateAlarmRepeat(int id, int bool){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_ALARM+" SET repeat = '"+bool+"' WHERE Id = '"+id+"'");
    }

    void updateMon(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_NOTE+" SET mon = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateAlarmMon(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_ALARM+" SET mon = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateTue(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_NOTE+" SET tue = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateAlarmTue(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_ALARM+" SET tue = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateWed(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_NOTE+" SET wed = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateAlarmWed(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_ALARM+" SET wed = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateThu(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_NOTE+" SET thu = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateAlarmThu(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_ALARM+" SET thu = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateFri(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_NOTE+" SET fri = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateAlarmFri(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_ALARM+" SET fri = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateSat(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_NOTE+" SET sat = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateAlarmSat(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_ALARM+" SET sat = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateSun(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_NOTE+" SET sun = '"+daySet+"' WHERE Id = '"+id+"'");
    }

    void updateAlarmSun(int id, int daySet){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("UPDATE "+TABLE_ALARM+" SET sun = '"+daySet+"' WHERE Id = '"+id+"'");
    }

}
