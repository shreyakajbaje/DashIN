package com.example.dashin.CustomerModule.models;

public class SmartSearchModel {
    String TITLE,IS_TITLE,AREA;
    long TIMES;

    public SmartSearchModel(String TITLE, String IS_TITLE, String AREA, long TIMES) {
        this.TITLE = TITLE;
        this.IS_TITLE = IS_TITLE;
        this.AREA = AREA;
        this.TIMES = TIMES;
    }

    public SmartSearchModel() {
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getIS_TITLE() {
        return IS_TITLE;
    }

    public void setIS_TAG(String IS_TITLE) {
        this.IS_TITLE = IS_TITLE;
    }

    public String getAREA() {
        return AREA;
    }

    public void setAREA(String AREA) {
        this.AREA = AREA;
    }

    public long getTIMES() {
        return TIMES;
    }

    public void setTIMES(long TIMES) {
        this.TIMES = TIMES;
    }
}
