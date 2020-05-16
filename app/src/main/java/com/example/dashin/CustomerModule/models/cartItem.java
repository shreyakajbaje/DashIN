package com.example.dashin.CustomerModule.models;

public class cartItem {
    private String Name;



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
    public cartItem(String name, int Quantity, int Price,boolean veg) {
        Name = name;
        this.Quantity = Quantity;
        this.Price = Price;
        VEG=veg;

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
