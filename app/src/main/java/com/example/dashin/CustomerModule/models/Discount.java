package com.example.dashin.CustomerModule.models;


import java.util.ArrayList;

public class Discount {
    private String duration, expiry;
    private int offp;
    private String code;
    private ArrayList<String> foods;

    public int getOffp() {
        return offp;
    }

    public void setOffp(int offp) {
        this.offp = offp;
    }

    public Discount()
    {

    }

    public ArrayList<String> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<String> foods) {
        this.foods = foods;
    }

    public String getDuration() {
        return duration;
    }


    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

