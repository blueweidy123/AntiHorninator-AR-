package com.blueweidy.myapplication;

public class Frag3_Alarm {
    private String aName;
    private int nID;
    private int time_of_day;
    private int alarm_minute;

    private int repeat;
    private int mon;
    private int tue;
    private int wed;
    private int thu;
    private int fri;
    private int sat;
    private int sun;
    private int state;

    public Frag3_Alarm(int id, String name, int time_of_day, int alarm_minute,
                       int repeat, int mon, int tue, int wed, int thu, int fri, int sat, int sun, int state){
        this.nID = id;
        this.aName = name;
        this.time_of_day = time_of_day;
        this.alarm_minute = alarm_minute;
        this.repeat = repeat;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.state  = state;
    }

    public int getstate() {
        return state;
    }

    public String getnName() {
        return aName;
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
        return alarm_minute;
    }

    public int getnID() {
        return nID;
    }

    public int getRepeat() {
        return repeat;
    }

}
