package com.example.dashin.CustomerModule.models;

import com.google.firebase.firestore.PropertyName;

public class Details {
    @PropertyName("item_id")
    String ITEM_ID;
    @PropertyName("item_cat")
    String ITEM_CAT;
    @PropertyName("item_name")
    String ITEM_NAME;
    @PropertyName("item_cost")
    long ITEM_COST;
    @PropertyName("n")
    long N;
    @PropertyName("sub_tot")
    long SUB_TOT;


    public Details(String ITEM_ID, String ITEM_CAT, String ITEM_NAME, long ITEM_COST, long n, long SUB_TOT) {
        this.ITEM_ID = ITEM_ID;
        this.ITEM_CAT = ITEM_CAT;
        this.ITEM_NAME = ITEM_NAME;
        this.ITEM_COST = ITEM_COST;
        N = n;
        this.SUB_TOT = SUB_TOT;
    }

    public Details() {
    }
    @PropertyName("item_id")
    public String getITEM_ID() {
        return ITEM_ID;
    }
    @PropertyName("item_id")
    public void setITEM_ID(String ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }
    @PropertyName("item_cat")
    public String getITEM_CAT() {
        return ITEM_CAT;
    }
    @PropertyName("item_cat")
    public void setITEM_CAT(String ITEM_CAT) {
        this.ITEM_CAT = ITEM_CAT;
    }
    @PropertyName("item_name")
    public String getITEM_NAME() {
        return ITEM_NAME;
    }
    @PropertyName("item_name")
    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }
    @PropertyName("item_cost")
    public long getITEM_COST() {
        return ITEM_COST;
    }
    @PropertyName("item_cost")
    public void setITEM_COST(long ITEM_COST) {
        this.ITEM_COST = ITEM_COST;
    }
    @PropertyName("n")
    public long getN() {
        return N;
    }
    @PropertyName("n")
    public void setN(long n) {
        N = n;
    }
    @PropertyName("sub_tot")
    public long getSUB_TOT() {
        return SUB_TOT;
    }
    @PropertyName("sub_tot")
    public void setSUB_TOT(long SUB_TOT) {
        this.SUB_TOT = SUB_TOT;
    }

}
