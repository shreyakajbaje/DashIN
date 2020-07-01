package com.example.dashin.CustomerModule.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.PropertyName;

@IgnoreExtraProperties
public class ModelMess {
    @PropertyName("busi_name")
    String BUSI_NAME;
    @PropertyName("busi_description")
    String BUSI_DESCRIPTION;
    @PropertyName("mess_description")
    public String getMESS_DESCRIPTION() {
        return MESS_DESCRIPTION;
    }
    @PropertyName("mess_description")
    public void setMESS_DESCRIPTION(String MESS_DESCRIPTION) {
        this.MESS_DESCRIPTION = MESS_DESCRIPTION;
    }

    @PropertyName("mess_description")
    String MESS_DESCRIPTION;
    @PropertyName("owner_contact")
    String OWNER_CONTACT;
    @PropertyName("address")
    String ADDRESS;
    @PropertyName("front_pic")
    String FRONT_PIC;
    @PropertyName("open_from")
    String OPEN_FROM;
    @PropertyName("open_till")
    String OPEN_TILL;
    @PropertyName("veg")
    public Boolean getVEG() {
        return VEG;
    }
    @PropertyName("veg")
    public void setVEG(Boolean VEG) {
        this.VEG = VEG;
    }
    @PropertyName("veg")
    Boolean VEG;
    @PropertyName("owner_name")
    public String getOWNER_NAME() {
        return OWNER_NAME;
    }
    @PropertyName("owner_name")
    public void setOWNER_NAME(String OWNER_NAME) {
        this.OWNER_NAME = OWNER_NAME;
    }
    @PropertyName("owner_name")
    String OWNER_NAME;
    @PropertyName("owner_image")
    public String getOWNER_IMAGE() {
        return OWNER_IMAGE;
    }
    @PropertyName("owner_image")
    public void setOWNER_IMAGE(String OWNER_IMAGE) {
        this.OWNER_IMAGE = OWNER_IMAGE;
    }
    @PropertyName("owner_image")
    String OWNER_IMAGE;
    @PropertyName("timestamp")
    Timestamp TimeStamp;
    @PropertyName("location")
    GeoPoint LOCATION;
    @PropertyName("costing")
    int COSTING;
    @PropertyName("discount")
    int DISCOUNT;
    @PropertyName("rating")
    double RATING;
    @PropertyName("total_reviews")
    public int getTOTAL_REVIEWS() {
        return TOTAL_REVIEWS;
    }
    @PropertyName("total_reviews")
    public void setTOTAL_REVIEWS(int TOTAL_REVIEWS) {
        this.TOTAL_REVIEWS = TOTAL_REVIEWS;
    }
    @PropertyName("total_reviews")
    int TOTAL_REVIEWS;
    @PropertyName("5star_review_count")
    int STAR_REVIEW_COUNT5;
    @PropertyName("4star_review_count")
    int STAR_REVIEW_COUNT4;
    @PropertyName("3star_review_count")
    int STAR_REVIEW_COUNT3;
    @PropertyName("2star_review_count")
    int STAR_REVIEW_COUNT2;
    @PropertyName("5star_review_count")
    public int getSTAR_REVIEW_COUNT5() {
        return STAR_REVIEW_COUNT5;
    }
    @PropertyName("5star_review_count")
    public void setSTAR_REVIEW_COUNT5(int STAR_REVIEW_COUNT5) {
        this.STAR_REVIEW_COUNT5 = STAR_REVIEW_COUNT5;
    }
    @PropertyName("4star_review_count")
    public int getSTAR_REVIEW_COUNT4() {
        return STAR_REVIEW_COUNT4;
    }
    @PropertyName("4star_review_count")
    public void setSTAR_REVIEW_COUNT4(int STAR_REVIEW_COUNT4) {
        this.STAR_REVIEW_COUNT4 = STAR_REVIEW_COUNT4;
    }
    @PropertyName("3star_review_count")
    public int getSTAR_REVIEW_COUNT3() {
        return STAR_REVIEW_COUNT3;
    }
    @PropertyName("3star_review_count")
    public void setSTAR_REVIEW_COUNT3(int STAR_REVIEW_COUNT3) {
        this.STAR_REVIEW_COUNT3 = STAR_REVIEW_COUNT3;
    }
    @PropertyName("2star_review_count")
    public int getSTAR_REVIEW_COUNT2() {
        return STAR_REVIEW_COUNT2;
    }
    @PropertyName("2star_review_count")
    public void setSTAR_REVIEW_COUNT2(int STAR_REVIEW_COUNT2) {
        this.STAR_REVIEW_COUNT2 = STAR_REVIEW_COUNT2;
    }
    @PropertyName("1star_review_count")
    public int getSTAR_REVIEW_COUNT1() {
        return STAR_REVIEW_COUNT1;
    }
    @PropertyName("1star_review_count")
    public void setSTAR_REVIEW_COUNT1(int STAR_REVIEW_COUNT1) {
        this.STAR_REVIEW_COUNT1 = STAR_REVIEW_COUNT1;
    }
    @PropertyName("1star_review_count")
    int STAR_REVIEW_COUNT1;
/*
 @PropertyName("busi_NAME")
    String BUSI_NAME;
    @PropertyName("front_pic")
    String FRONT_PIC;
    @PropertyName("open_from")
    String OPEN_FROM;
    @PropertyName("open_till")
    String OPEN_TILL;
    @PropertyName("owner_name")
    String OWNER_NAME;
    @PropertyName("owner_contact")
    String OWNER_CONTACT;
    @PropertyName("owner_email")
    String OWNER_EMAIL;
    @PropertyName("owner_image")
    String OWNER_IMAGE;
    @PropertyName("busi_description")
    public String BUSI_DESCRIPTION;
    @PropertyName("mess_address")
    public String ADDRESS;
    @PropertyName("timestamp")
    Timestamp TimeStamp;
    @PropertyName("location")
    GeoPoint LOCATION;
    @PropertyName("costing")
    int COSTING;
    @PropertyName("discount")
    int DISCOUNT;
    @PropertyName("rating")
    double RATING;
 */
    public ModelMess(String BUSI_NAME, String MESS_DESCRIPTION, String OWNER_CONTACT, String ADDRESS, int COSTING, String FRONT_PIC, String OPEN_FROM, String OPEN_TILL, Timestamp timeStamp, GeoPoint LOCATION, double RATING,String owner_image,String owner_name,Boolean veg,int total_reviews,int rcnt1,int rcnt2,
    int rcnt3,int rcnt4,int rcnt5) {
        this.BUSI_NAME = BUSI_NAME;
        this.MESS_DESCRIPTION = MESS_DESCRIPTION;
        this.OWNER_CONTACT = OWNER_CONTACT;
        this.ADDRESS = ADDRESS;
        this.COSTING = COSTING;
        this.FRONT_PIC = FRONT_PIC;
        this.OPEN_FROM = OPEN_FROM;
        this.OPEN_TILL = OPEN_TILL;
        this.LOCATION = LOCATION;
        this.RATING = RATING;
        this.OWNER_IMAGE=owner_image;
        this.OWNER_NAME=owner_name;
        this.VEG=veg;
        this.TOTAL_REVIEWS=total_reviews;
        this.STAR_REVIEW_COUNT1=rcnt1;
        this.STAR_REVIEW_COUNT2=rcnt2;
        this.STAR_REVIEW_COUNT3=rcnt3;
        this.STAR_REVIEW_COUNT4=rcnt4;
        this.STAR_REVIEW_COUNT5=rcnt5;

    }

    public ModelMess(){}


    @Override
    public String toString() {
        return "ModelMess{" +
                "BUSI_NAME='" + BUSI_NAME + '\'' +
                ", BUSI_DESCRIPTION='" + BUSI_DESCRIPTION + '\'' +
                ", owner_CONTACT='" + OWNER_CONTACT + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", COSTING='" + COSTING + '\'' +
                ", front_PIC='" + FRONT_PIC + '\'' +
                ", OPEN_FROM='" + OPEN_FROM + '\'' +
                ", OPEN_TILL='" + OPEN_TILL + '\'' +
                ", TimeStamp=" + TimeStamp +
                ", LOCATION=" + LOCATION +
                ", RATING=" + RATING +
                '}';
    }@PropertyName("discount")

    public int getDISCOUNT() {
        return DISCOUNT;
    }
    @PropertyName("discount")
    public void setDISCOUNT(int DISCOUNT) {
        this.DISCOUNT = DISCOUNT;
    }
    @PropertyName("busi_name")
    public String getBUSI_NAME() {
        return BUSI_NAME;
    }
    @PropertyName("busi_name")
    public void setBUSI_NAME(String BUSI_NAME) {
        this.BUSI_NAME = BUSI_NAME;
    }
    @PropertyName("busi_description")
    public String getBUSI_DESCRIPTION() {
        return BUSI_DESCRIPTION;
    }
    @PropertyName("busi_description")
    public void setBUSI_DESCRIPTION(String BUSI_DESCRIPTION) {
        this.BUSI_DESCRIPTION = BUSI_DESCRIPTION;
    }


    @PropertyName("address")
    public String getADDRESS() {
        return ADDRESS;
    }
    @PropertyName("address")
    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }
    @PropertyName("costing")
    public int getCOSTING() {
        return COSTING;
    }
    @PropertyName("costing")
    public void setCOSTING(int COSTING) {
        this.COSTING = COSTING;
    }
    @PropertyName("owner_contact")
    public String getOWNER_CONTACT() {
        return OWNER_CONTACT;
    }
    @PropertyName("owner_contact")
    public void setOWNER_CONTACT(String OWNER_CONTACT) {
        this.OWNER_CONTACT = OWNER_CONTACT;
    }
    @PropertyName("front_pic")
    public String getFRONT_PIC() {
        return FRONT_PIC;
    }
    @PropertyName("front_pic")
    public void setFRONT_PIC(String FRONT_PIC) {
        this.FRONT_PIC = FRONT_PIC;
    }
    @PropertyName("open_from")
    public String getOPEN_FROM() {
        return OPEN_FROM;
    }
    @PropertyName("open_from")
    public void setOPEN_FROM(String OPEN_FROM) {
        this.OPEN_FROM = OPEN_FROM;
    }
    @PropertyName("open_till")
    public String getOPEN_TILL() {
        return OPEN_TILL;
    }
    @PropertyName("open_till")
    public void setOPEN_TILL(String OPEN_TILL) {
        this.OPEN_TILL = OPEN_TILL;
    }
    @PropertyName("timestamp")
    public Timestamp getTimeStamp() {
        return TimeStamp;
    }
    @PropertyName("timestamp")
    public void setTimeStamp(Timestamp timeStamp) {
        TimeStamp = timeStamp;
    }
    @PropertyName("location")
    public GeoPoint getLOCATION() {
        return LOCATION;
    }
    @PropertyName("location")
    public void setLOCATION(GeoPoint LOCATION) {
        this.LOCATION = LOCATION;
    }
    @PropertyName("rating")
    public double getRATING() {
        return RATING;
    }
    @PropertyName("rating")
    public void setRATING(double RATING) {
        this.RATING = RATING;
    }
}