package com.blueweidy.myapplication;

public interface note_update {
    void updateNoteName(String name, int id, boolean update);
    void updateTime(int hh, int mm, int id, boolean update);
    void delete(String name, int id, boolean update);
    void repeat(int id, int Repeatboolean, boolean update);
    void sundaySet(int id, int daySet, boolean update);
    void mondaySet(int id, int daySet, boolean update);
    void tuedaySet(int id, int daySet, boolean update);
    void wednesdaySet(int id, int daySet, boolean update);
    void thursdaySet(int id, int daySet, boolean update);
    void fridaySet(int id, int daySet, boolean update);
    void saturdaySet(int id, int daySet, boolean update);
    void turn(int id, int switchboolean, boolean update);
}
