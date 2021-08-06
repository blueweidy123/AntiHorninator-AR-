package com.blueweidy.myapplication;

public class ScreenItem {

    String Title, Desc;
    int ScreenImg;

    public ScreenItem(String title, String desc, int screenImg) {
        Title = title;
        Desc = desc;
        ScreenImg = screenImg;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }

    public String getTitle() {
        return Title;
    }

    public String getDesc() {
        return Desc;
    }

    public int getScreenImg() {
        return ScreenImg;
    }
}
