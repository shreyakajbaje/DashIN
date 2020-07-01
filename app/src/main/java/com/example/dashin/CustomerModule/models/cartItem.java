package com.example.dashin.CustomerModule.models;

import com.google.firebase.firestore.PropertyName;

public class cartItem {
    @PropertyName("name")
    private String Name;
    @PropertyName("price")
    private int Price;
    @PropertyName("quantity")
    private int Quantity;
    @PropertyName("type")
    public String getType() {
        return Type;
    }
    @PropertyName("type")
    public void setType(String type) {
        Type = type;
    }
    @PropertyName("type")
    private String Type;
    @PropertyName("veg")
    public boolean isVEG() {
        return VEG;
    }
    @PropertyName("veg")
    public void setVEG(boolean VEG) {
        this.VEG = VEG;
    }
    @PropertyName("veg")
    private boolean VEG;
    public cartItem()
    {

    }
    public cartItem(String name, int Quantity, int Price,boolean veg,String Type) {
        Name = name;
        this.Quantity = Quantity;
        this.Price = Price;
        VEG=veg;
        this.Type=Type;

    }


    @PropertyName("quantity")
    public int getQuantity() {
        return Quantity;
    }
    @PropertyName("quantity")
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    @PropertyName("name")
    public String getName() {
        return Name;
    }
    @PropertyName("name")
    public void setName(String name) {
        Name = name;
    }


    @PropertyName("price")
    public int getPrice() {
        return Price;
    }
    @PropertyName("price")
    public void setPrice(int Price) {
        this.Price = Price;
    }


    
}
