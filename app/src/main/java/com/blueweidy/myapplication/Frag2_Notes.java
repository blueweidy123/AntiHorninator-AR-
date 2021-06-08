package com.blueweidy.myapplication;

public class Frag2_Notes {
    private String nName;
    private int nID;
    private int time_of_day;
    private int note_minute;

    private int repeat;
    private int mon;
    private int tue;
    private int wed;
    private int thu;
    private int fri;
    private int sat;
    private int sun;

    public Frag2_Notes(int id, String name, int time_of_day, int note_minute,
                       int repeat, int mon, int tue, int wed, int thu, int fri, int sat, int sun){
        this.nID = id;
        this.nName = name;
        this.time_of_day = time_of_day;
        this.note_minute = note_minute;
        this.repeat = repeat;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }

    public String getnName() {
        return nName;
    }

    public int getMon() {
        return mon;
    }

    public int getTue() {
        return tue;
    }

    public int getWed() {
        return wed;
    }

    public int getThu() {
        return thu;
    }

    public int getFri() {
        return fri;
    }

    public int getSat() {
        return sat;
    }

    public int getSun() {
        return sun;
    }

    public int getTime_of_day() {
        return time_of_day;
    }

    public int getNote_minute() {
        return note_minute;
    }

    public int getnID() {
        return nID;
    }

    public int getRepeat() {
        return repeat;
    }

}
