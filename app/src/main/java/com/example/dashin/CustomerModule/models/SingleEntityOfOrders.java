package com.example.dashin.CustomerModule.models;


import com.google.firebase.firestore.PropertyName;
import com.google.j2objc.annotations.Property;

import java.util.ArrayList;


public class SingleEntityOfOrders {
    @PropertyName("busi_name")
    String BUSI_NAME;
    @PropertyName("busi_add")
    String BUSI_ADD;
    @PropertyName("d_r_index")
    String D_R_INDEX;
    @PropertyName("offer_code")
    String OFFER_CODE;
    @PropertyName("tran_id")
    String TRAN_ID;
    @PropertyName("method")
    String METHOD;
    @PropertyName("type")
    String TYPE;
    @PropertyName("from")
    String FROM;
    @PropertyName("to")
    String TO;
    @PropertyName("time")
    String TIME;
    @PropertyName("busi_loc")
    ArrayList<String> BUSI_LOC;
    @PropertyName("cust_loc")
    public ArrayList<String> getCUST_LOC() {
        return CUST_LOC;
    }
    @PropertyName("cust_loc")
    public void setCUST_LOC(ArrayList<String> CUST_LOC) {
        this.CUST_LOC = CUST_LOC;
    }

    @PropertyName("cust_loc")
    ArrayList<String> CUST_LOC;
    @PropertyName("amount")
    long AMOUNT;
    @PropertyName("status")
    long STATUS;
    @PropertyName("offer")
    boolean OFFER;
    @PropertyName("liked")
    boolean LIKED=false;
    @PropertyName("delivery_address")
    String DELIVERY_ADDRESS;
    @PropertyName("delivery_address")
    public String getDELIVERY_ADDRESS() {
        return DELIVERY_ADDRESS;
    }
    @PropertyName("front_pic")
    public String getFRONT_PIC() {
        return FRONT_PIC;
    }
    @PropertyName("front_pic")
    public void setFRONT_PIC(String FRONT_PIC) {
        this.FRONT_PIC = FRONT_PIC;
    }

    @PropertyName("front_pic")
    String FRONT_PIC;
    public SingleEntityOfOrders(String BUSI_NAME, String BUSI_ADD, String d_R_INDEX, String OFFER_CODE, String TRAN_ID, String METHOD, String TYPE, String FROM, String TO, String TIME, ArrayList<String> BUSI_LOC, long AMOUNT, long STATUS, boolean OFFER, boolean LIKED, String DELIVERY_ADDRESS, String PERSON_NAME_ADDRESS,ArrayList<String> CUST_LOC,String front_pic) {
        this.BUSI_NAME = BUSI_NAME;
        this.BUSI_ADD = BUSI_ADD;
        D_R_INDEX = d_R_INDEX;
        this.OFFER_CODE = OFFER_CODE;
        this.TRAN_ID = TRAN_ID;
        this.METHOD = METHOD;
        this.TYPE = TYPE;
        this.FROM = FROM;
        this.TO = TO;
        this.TIME = TIME;
        this.BUSI_LOC = BUSI_LOC;
        this.AMOUNT = AMOUNT;
        this.STATUS = STATUS;
        this.OFFER = OFFER;
        this.LIKED = LIKED;
        this.DELIVERY_ADDRESS = DELIVERY_ADDRESS;
        this.PERSON_NAME_ADDRESS = PERSON_NAME_ADDRESS;
        this.CUST_LOC=CUST_LOC;
        this.FRONT_PIC=front_pic;
    }
    @PropertyName("delivery_address")
    public void setDELIVERY_ADDRESS(String DELIVERY_ADDRESS) {
        this.DELIVERY_ADDRESS = DELIVERY_ADDRESS;
    }
    @PropertyName("person_name_address")
    public String getPERSON_NAME_ADDRESS() {
        return PERSON_NAME_ADDRESS;
    }
    @PropertyName("person_name_address")
    public void setPERSON_NAME_ADDRESS(String PERSON_NAME_ADDRESS) {
        this.PERSON_NAME_ADDRESS = PERSON_NAME_ADDRESS;
    }
    @PropertyName("person_name_address")
    String PERSON_NAME_ADDRESS;
    public SingleEntityOfOrders()
    {

    }


    @PropertyName("busi_name")
    public String getBUSI_NAME() {
        return BUSI_NAME;
    }
    @PropertyName("busi_add")
    public String getBUSI_ADD() {
        return BUSI_ADD;
    }
    @PropertyName("d_r_index")
    public String getD_R_INDEX() {
        return D_R_INDEX;
    }
    @PropertyName("offer_code")
    public String getOFFER_CODE() {
        return OFFER_CODE;
    }
    @PropertyName("tran_id")
    public String getTRAN_ID() {
        return TRAN_ID;
    }
    @PropertyName("method")
    public String getMETHOD() {
        return METHOD;
    }
    @PropertyName("type")
    public String getTYPE() {
        return TYPE;
    }
    @PropertyName("from")
    public String getFROM() {
        return FROM;
    }
    @PropertyName("to")
    public String getTO() {
        return TO;
    }
    @PropertyName("time")
    public String getTIME() {
        return TIME;
    }
    @PropertyName("busi_loc")
    public ArrayList<String> getBUSI_LOC() {
        return BUSI_LOC;
    }
    @PropertyName("amount")
    public long getAMOUNT() {
        return AMOUNT;
    }
    @PropertyName("status")
    public long getSTATUS() {
        return STATUS;
    }
    @PropertyName("offer")
    public boolean isOFFER() {
        return OFFER;
    }
    @PropertyName("busi_name")
    public void setBUSI_NAME(String BUSI_NAME) {
        this.BUSI_NAME = BUSI_NAME;
    }
    @PropertyName("busi_add")
    public void setBUSI_ADD(String BUSI_ADD) {
        this.BUSI_ADD = BUSI_ADD;
    }
    @PropertyName("d_r_index")
    public void setD_R_INDEX(String d_R_INDEX) {
        D_R_INDEX = d_R_INDEX;
    }
    @PropertyName("offer_code")
    public void setOFFER_CODE(String OFFER_CODE) {
        this.OFFER_CODE = OFFER_CODE;
    }
    @PropertyName("tran_id")
    public void setTRAN_ID(String TRAN_ID) {
        this.TRAN_ID = TRAN_ID;
    }
    @PropertyName("method")
    public void setMETHOD(String METHOD) {
        this.METHOD = METHOD;
    }
    @PropertyName("type")
    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
    @PropertyName("from")
    public void setFROM(String FROM) {
        this.FROM = FROM;
    }
    @PropertyName("to")
    public void setTO(String TO) {
        this.TO = TO;
    }
    @PropertyName("time")
    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
    @PropertyName("busi_loc")
    public void setBUSI_LOC(ArrayList<String> BUSI_LOC) {
        this.BUSI_LOC = BUSI_LOC;
    }
    @PropertyName("amount")
    public void setAMOUNT(long AMOUNT) {
        this.AMOUNT = AMOUNT;
    }
    @PropertyName("status")
    public void setSTATUS(long STATUS) {
        this.STATUS = STATUS;
    }
    @PropertyName("offer")
    public void setOFFER(boolean OFFER) {
        this.OFFER = OFFER;
    }
    @PropertyName("liked")
    public boolean isLIKED() {
        return LIKED;
    }
    @PropertyName("liked")
    public void setLIKED(boolean LIKED) {
        this.LIKED = LIKED;
    }
}
