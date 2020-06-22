package com.example.dashin.CustomerModule.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

@IgnoreExtraProperties
public class ModelMess {
    String BUSI_NAME, BUSI_DESCRIPTION, owner_CONTACT, ADDRESS, front_PIC, OPEN_FROM, OPEN_TILL;
    Timestamp TimeStamp;
    GeoPoint LOCATION;
    int COSTING, DISCOUNT;
    double RATING;
    ArrayList<String> FACILITIES = new ArrayList<>();
    ArrayList<String> Mess_IMAGES = new ArrayList<>();

    public ModelMess(String BUSI_NAME, String BUSI_DESCRIPTION, String owner_CONTACT, String ADDRESS, int COSTING, String front_PIC, String OPEN_FROM, String OPEN_TILL, Timestamp timeStamp, GeoPoint LOCATION, double RATING) {
        this.BUSI_NAME = BUSI_NAME;
        this.BUSI_DESCRIPTION = BUSI_DESCRIPTION;
        this.owner_CONTACT = owner_CONTACT;
        this.ADDRESS = ADDRESS;
        this.COSTING = COSTING;
        this.front_PIC = front_PIC;
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
                ", owner_CONTACT='" + owner_CONTACT + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", COSTING='" + COSTING + '\'' +
                ", front_PIC='" + front_PIC + '\'' +
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



    public String getADDRESS() {
        return ADDRESS;
    }

    @PropertyName("MESS_ADDRESS")
    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public int getCOSTING() {
        return COSTING;
    }

    public void setCOSTING(int COSTING) {
        this.COSTING = COSTING;
    }

    public String getOwner_CONTACT() {
        return owner_CONTACT;
    }

    public void setOwner_CONTACT(String owner_CONTACT) {
        this.owner_CONTACT = owner_CONTACT;
    }

    public String getFront_PIC() {
        return front_PIC;
    }

    public void setFront_PIC(String front_PIC) {
        this.front_PIC = front_PIC;
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
/*
    public ArrayList<String> getFACILITIES() {
        return FACILITIES;
    }

    public void setFACILITIES(ArrayList<String> FACILITIES) {
        this.FACILITIES = FACILITIES;
    }

    public ArrayList<String> getMess_IMAGES() {
        return Mess_IMAGES;
    }

    public void setMess_IMAGES(ArrayList<String> mess_IMAGES) {
        Mess_IMAGES = mess_IMAGES;
    }
*/

}