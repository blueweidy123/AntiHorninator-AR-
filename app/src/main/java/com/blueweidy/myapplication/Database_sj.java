package com.blueweidy.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class Database_sj extends SQLiteOpenHelper {

    //region init
    private static final String DATABASE_NAME = "SJ_data";
    private static final int DATABASE_VERSION = 1;
    private static final String Table_sj_1 = "subjectMon";
    private static final String Table_sj_2 = "subjectTue";
    private static final String Table_sj_3 = "subjectWed";
    private static final String Table_sj_4 = "subjectThu";
    private static final String Table_sj_5 = "subjectFri";
    private static final String Table_sj_6 = "subjectSat";
    private static final String Table_sj_7 = "subjectSun";
    //endregion

    public Database_sj(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_table_mon = "CREATE TABLE IF NOT EXISTS " + Table_sj_1 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        String Create_table_tue = "CREATE TABLE IF NOT EXISTS " + Table_sj_2 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        String Create_table_wed = "CREATE TABLE IF NOT EXISTS " + Table_sj_3 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        String Create_table_thu = "CREATE TABLE IF NOT EXISTS " + Table_sj_4 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        String Create_table_fri = "CREATE TABLE IF NOT EXISTS " + Table_sj_5 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        String Create_table_sat = "CREATE TABLE IF NOT EXISTS " + Table_sj_6 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        String Create_table_sun = "CREATE TABLE IF NOT EXISTS " + Table_sj_7 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";

        db.execSQL(Create_table_mon);
        db.execSQL(Create_table_tue);
        db.execSQL(Create_table_wed);
        db.execSQL(Create_table_thu);
        db.execSQL(Create_table_fri);
        db.execSQL(Create_table_sat);
        db.execSQL(Create_table_sun);
    }

    public void createSJ_1(){
        SQLiteDatabase database = getWritableDatabase();
        String Create_table_mon = "CREATE TABLE IF NOT EXISTS " + Table_sj_1 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        database.execSQL(Create_table_mon);
    }
    public void createSJ_2(){
        SQLiteDatabase database = getWritableDatabase();
        String Create_table_mon = "CREATE TABLE IF NOT EXISTS " + Table_sj_2 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        database.execSQL(Create_table_mon);
    }
    public void createSJ_3(){
        SQLiteDatabase database = getWritableDatabase();
        String Create_table_mon = "CREATE TABLE IF NOT EXISTS " + Table_sj_3 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        database.execSQL(Create_table_mon);
    }
    public void createSJ_4(){
        SQLiteDatabase database = getWritableDatabase();
        String Create_table_mon = "CREATE TABLE IF NOT EXISTS " + Table_sj_4 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        database.execSQL(Create_table_mon);
    }
    public void createSJ_5(){
        SQLiteDatabase database = getWritableDatabase();
        String Create_table_mon = "CREATE TABLE IF NOT EXISTS " + Table_sj_5 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        database.execSQL(Create_table_mon);
    }
    public void createSJ_6(){
        SQLiteDatabase database = getWritableDatabase();
        String Create_table_mon = "CREATE TABLE IF NOT EXISTS " + Table_sj_6 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        database.execSQL(Create_table_mon);
    }
    public void createSJ_7(){
        SQLiteDatabase database = getWritableDatabase();
        String Create_table_mon = "CREATE TABLE IF NOT EXISTS " + Table_sj_7 + " (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NameSJ VARCHAR (255))";
        database.execSQL(Create_table_mon);
    }

    public void deleteTable(int getTable){
        SQLiteDatabase database = getWritableDatabase();
        if (getTable == 1){
            database.execSQL("DROP TABLE IF EXISTS "+ Table_sj_1);
        }else if (getTable == 2){
            database.execSQL("DROP TABLE IF EXISTS "+ Table_sj_2);
        }else if (getTable == 3){
            database.execSQL("DROP TABLE IF EXISTS "+ Table_sj_3);
        }else if (getTable == 4){
            database.execSQL("DROP TABLE IF EXISTS "+ Table_sj_4);
        }else if (getTable == 5){
            database.execSQL("DROP TABLE IF EXISTS "+ Table_sj_5);
        }else if (getTable == 6){
            database.execSQL("DROP TABLE IF EXISTS "+ Table_sj_6);
        }else if (getTable == 7){
            database.execSQL("DROP TABLE IF EXISTS "+ Table_sj_7);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Table_sj_1);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_sj_2);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_sj_3);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_sj_4);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_sj_5);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_sj_6);
        db.execSQL("DROP TABLE IF EXISTS "+ Table_sj_7);
    }

    public List<subject> getAll_mon_Subjects(){
        List<subject> SJ_List = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+Table_sj_1, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            SJ_List.add(new subject(name, id));
        }
        cursor.close();
        return SJ_List;
    }

    public List<subject> getAll_tue_Subjects(){
        List<subject> SJ_List = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+Table_sj_2, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            SJ_List.add(new subject(name, id));
        }
        cursor.close();
        return SJ_List;
    }

    public List<subject> getAll_wed_Subjects(){
        List<subject> SJ_List = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+Table_sj_3, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            SJ_List.add(new subject(name, id));
        }
        cursor.close();
        return SJ_List;
    }

    public List<subject> getAll_thu_Subjects(){
        List<subject> SJ_List = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+Table_sj_4, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            SJ_List.add(new subject(name, id));
        }
        cursor.close();
        return SJ_List;
    }

    public List<subject> getAll_fri_Subjects(){
        List<subject> SJ_List = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+Table_sj_5, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            SJ_List.add(new subject(name, id));
        }
        cursor.close();
        return SJ_List;
    }

    public List<subject> getAll_sat_Subjects(){
        List<subject> SJ_List = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+Table_sj_6, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            SJ_List.add(new subject(name, id));
        }
        cursor.close();
        return SJ_List;
    }

    public List<subject> getAll_sun_Subjects(){
        List<subject> SJ_List = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+Table_sj_7, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            SJ_List.add(new subject(name, id));
        }
        cursor.close();
        return SJ_List;
    }

    void updateName(int table, String name, int id){
        SQLiteDatabase database = getWritableDatabase();
        if (table == 1){
            database.execSQL("UPDATE "+Table_sj_1+" SET NameSJ = '"+name+"' WHERE Id = '"+id+"'");
        }else if (table == 2){
            database.execSQL("UPDATE "+Table_sj_2+" SET NameSJ = '"+name+"' WHERE Id = '"+id+"'");
        }else if (table == 3){
            database.execSQL("UPDATE "+Table_sj_3+" SET NameSJ = '"+name+"' WHERE Id = '"+id+"'");
        }else if (table == 4){
            database.execSQL("UPDATE "+Table_sj_4+" SET NameSJ = '"+name+"' WHERE Id = '"+id+"'");
        }else if (table == 5){
            database.execSQL("UPDATE "+Table_sj_5+" SET NameSJ = '"+name+"' WHERE Id = '"+id+"'");
        }else if (table == 6){
            database.execSQL("UPDATE "+Table_sj_6+" SET NameSJ = '"+name+"' WHERE Id = '"+id+"'");
        }else if (table == 7){
            database.execSQL("UPDATE "+Table_sj_7+" SET NameSJ = '"+name+"' WHERE Id = '"+id+"'");
        }
    }

    void delete(int table, int id){
        SQLiteDatabase database = getWritableDatabase();
        if (table == 1){
            database.execSQL("DELETE FROM "+Table_sj_1+" WHERE Id = '"+ id +"'");
        }else if (table == 2){
            database.execSQL("DELETE FROM "+Table_sj_2+" WHERE Id = '"+ id +"'");
        }else if (table == 3){
            database.execSQL("DELETE FROM "+Table_sj_3+" WHERE Id = '"+ id +"'");
        }else if (table == 4){
            database.execSQL("DELETE FROM "+Table_sj_4+" WHERE Id = '"+ id +"'");
        }else if (table == 5){
            database.execSQL("DELETE FROM "+Table_sj_5+" WHERE Id = '"+ id +"'");
        }else if (table == 6){
            database.execSQL("DELETE FROM "+Table_sj_6+" WHERE Id = '"+ id +"'");
        }else if (table == 7){
            database.execSQL("DELETE FROM "+Table_sj_7+" WHERE Id = '"+ id +"'");
        }
    }

    void insert(int table, String name){
        SQLiteDatabase database = getWritableDatabase();
        if (table == 1){
            database.execSQL("INSERT INTO "+ Table_sj_1+" VALUES(null, '"+ name +"')");
        }else if (table == 2){
            database.execSQL("INSERT INTO "+ Table_sj_2+" VALUES(null, '"+ name +"')");
        }else if (table == 3){
            database.execSQL("INSERT INTO "+ Table_sj_3+" VALUES(null, '"+ name +"')");
        }else if (table == 4){
            database.execSQL("INSERT INTO "+ Table_sj_4+" VALUES(null, '"+ name +"')");
        }else if (table == 5){
            database.execSQL("INSERT INTO "+ Table_sj_5+" VALUES(null, '"+ name +"')");
        }else if (table == 6){
            database.execSQL("INSERT INTO "+ Table_sj_6+" VALUES(null, '"+ name +"')");
        }else if (table == 7){
            database.execSQL("INSERT INTO "+ Table_sj_7+" VALUES(null, '"+ name +"')");
        }
    }
}
