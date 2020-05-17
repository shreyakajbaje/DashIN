package com.example.dashin.CustomerModule.models;

public class Details {
    String ITEM_ID;
    String ITEM_CAT;
    String ITEM_NAME;
    long ITEM_COST;
    long N;
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

    public String getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(String ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    public String getITEM_CAT() {
        return ITEM_CAT;
    }

    public void setITEM_CAT(String ITEM_CAT) {
        this.ITEM_CAT = ITEM_CAT;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public long getITEM_COST() {
        return ITEM_COST;
    }

    public void setITEM_COST(long ITEM_COST) {
        this.ITEM_COST = ITEM_COST;
    }

    public long getN() {
        return N;
    }

    public void setN(long n) {
        N = n;
    }

    public long getSUB_TOT() {
        return SUB_TOT;
    }

    public void setSUB_TOT(long SUB_TOT) {
        this.SUB_TOT = SUB_TOT;
    }

}
