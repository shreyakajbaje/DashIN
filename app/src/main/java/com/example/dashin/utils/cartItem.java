package com.example.dashin.utils;

public class cartItem {
    private String Name;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private String ID;
    private int Price;
    private int Quantity;

    public boolean isVEG() {
        return VEG;
    }

    public void setVEG(boolean VEG) {
        this.VEG = VEG;
    }

    private boolean VEG;
    public cartItem()
    {

    }
    public cartItem(String name, int Quantity, int Price,boolean veg,String id) {
        Name = name;
        this.Quantity = Quantity;
        this.Price = Price;
        VEG=veg;
        ID=id;
    }



    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }


    
}
