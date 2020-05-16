package com.example.dashin.CustomerModule.models;

public class Details {
    public String ITEM_ID;
    public String ITEM_CAT;
    public String ITEM_NAME;
    public long ITEM_COST;
    public long N;
    public long SUB_TOTAL;

    public Details(String ITEM_ID, String ITEM_CAT, String ITEM_NAME, long ITEM_COST, long n, long SUB_TOTAL) {
        this.ITEM_ID = ITEM_ID;
        this.ITEM_CAT = ITEM_CAT;
        this.ITEM_NAME = ITEM_NAME;
        this.ITEM_COST = ITEM_COST;
        N = n;
        this.SUB_TOTAL = SUB_TOTAL;
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

    public long getSUB_TOTAL() {
        return SUB_TOTAL;
    }

    public void setSUB_TOTAL(long SUB_TOTAL) {
        this.SUB_TOTAL = SUB_TOTAL;
    }

}
