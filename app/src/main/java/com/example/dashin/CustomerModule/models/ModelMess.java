package com.example.dashin.CustomerModule.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ModelMess {
    String BUSI_NAME, BUSI_DESCRIPTION, OWNER, ADDRESS, FRONT_PIC, OPEN_FROM, OPEN_TILL;
    Timestamp TimeStamp;
    GeoPoint LOCATION;
    int COSTING, DISCOUNT;
    double RATING;

    public ModelMess(String BUSI_NAME, String BUSI_DESCRIPTION, String OWNER, String ADDRESS, int COSTING, String FRONT_PIC, String OPEN_FROM, String OPEN_TILL, Timestamp timeStamp, GeoPoint LOCATION, double RATING) {
        this.BUSI_NAME = BUSI_NAME;
        this.BUSI_DESCRIPTION = BUSI_DESCRIPTION;
        this.OWNER = OWNER;
        this.ADDRESS = ADDRESS;
        this.COSTING = COSTING;
        this.FRONT_PIC = FRONT_PIC;
        this.OPEN_FROM = OPEN_FROM;
        this.OPEN_TILL = OPEN_TILL;
        this.LOCATION = LOCATION;
        this.RATING = RATING;
    }

    public ModelMess(){}


    @Override
    public String toString() {
        return "ModelMess{" +
                "BUSI_NAME='" + BUSI_NAME + '\'' +
                ", BUSI_DESCRIPTION='" + BUSI_DESCRIPTION + '\'' +
                ", OWNER='" + OWNER + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", COSTING='" + COSTING + '\'' +
                ", FRONT_PIC='" + FRONT_PIC + '\'' +
                ", OPEN_FROM='" + OPEN_FROM + '\'' +
                ", OPEN_TILL='" + OPEN_TILL + '\'' +
                ", TimeStamp=" + TimeStamp +
                ", LOCATION=" + LOCATION +
                ", RATING=" + RATING +
                '}';
    }

    public int getDISCOUNT() {
        return DISCOUNT;
    }

    public void setDISCOUNT(int DISCOUNT) {
        this.DISCOUNT = DISCOUNT;
    }

    public String getBUSI_NAME() {
        return BUSI_NAME;
    }

    public void setBUSI_NAME(String BUSI_NAME) {
        this.BUSI_NAME = BUSI_NAME;
    }

    public String getBUSI_DESCRIPTION() {
        return BUSI_DESCRIPTION;
    }

    public void setBUSI_DESCRIPTION(String BUSI_DESCRIPTION) {
        this.BUSI_DESCRIPTION = BUSI_DESCRIPTION;
    }

    public String getOWNER() {
        return OWNER;
    }

    public void setOWNER(String OWNER) {
        this.OWNER = OWNER;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public int getCOSTING() {
        return COSTING;
    }

    public void setCOSTING(int COSTING) {
        this.COSTING = COSTING;
    }

    public String getFRONT_PIC() {
        return FRONT_PIC;
    }

    public void setFRONT_PIC(String FRONT_PIC) {
        this.FRONT_PIC = FRONT_PIC;
    }

    public String getOPEN_FROM() {
        return OPEN_FROM;
    }

    public void setOPEN_FROM(String OPEN_FROM) {
        this.OPEN_FROM = OPEN_FROM;
    }

    public String getOPEN_TILL() {
        return OPEN_TILL;
    }

    public void setOPEN_TILL(String OPEN_TILL) {
        this.OPEN_TILL = OPEN_TILL;
    }

    public Timestamp getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        TimeStamp = timeStamp;
    }

    public GeoPoint getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(GeoPoint LOCATION) {
        this.LOCATION = LOCATION;
    }

    public double getRATING() {
        return RATING;
    }

    public void setRATING(double RATING) {
        this.RATING = RATING;
    }
}