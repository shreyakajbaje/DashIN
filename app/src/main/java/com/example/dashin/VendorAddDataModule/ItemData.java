package com.example.dashin.VendorAddDataModule;

public class ItemData {
    String ITEM_NAME;
    long ITEM_PRICE;
    String ITEM_DESC;
    String INT_ITEM_ID;
    String CAT_ID;

    public ItemData(String ITEM_NAME, long ITEM_PRICE, String ITEM_DESC, String INT_ITEM_ID, String CAT_ID) {
        this.ITEM_NAME = ITEM_NAME;
        this.ITEM_PRICE = ITEM_PRICE;
        this.ITEM_DESC = ITEM_DESC;
        this.INT_ITEM_ID = INT_ITEM_ID;
        this.CAT_ID=CAT_ID;
    }

    public ItemData() {

    }

    public String getINT_ITEM_ID() {
        return INT_ITEM_ID;
    }

    public void setINT_ITEM_ID(String INT_ITEM_ID) {
        this.INT_ITEM_ID = INT_ITEM_ID;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public long getITEM_PRICE() {
        return ITEM_PRICE;
    }

    public void setITEM_PRICE(long ITEM_PRICE) {
        this.ITEM_PRICE = ITEM_PRICE;
    }

    public String getITEM_DESC() {
        return ITEM_DESC;
    }

    public void setITEM_DESC(String ITEM_DESC) {
        this.ITEM_DESC = ITEM_DESC;
    }

    public String getCAT_ID() {
        return CAT_ID;
    }

    public void setCAT_ID(String CAT_ID) {
        this.CAT_ID = CAT_ID;
    }
}
