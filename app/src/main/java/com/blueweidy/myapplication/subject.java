package com.blueweidy.myapplication;

public class subject {

    private int subjectID;
    private String subjectName;


    public subject(String subjectName, int subjectID) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }
}
