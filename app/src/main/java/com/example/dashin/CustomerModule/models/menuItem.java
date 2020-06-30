package com.example.dashin.CustomerModule.models;

import com.google.firebase.firestore.PropertyName;

public class menuItem {
    @PropertyName("name")
    String Name;
    @PropertyName("description")
    String Description;
    @PropertyName("veg")
    public boolean isVEG() {
        return VEG;
    }
    @PropertyName("veg")
    public void setVEG(boolean VEG) {
        this.VEG = VEG;
    }
    @PropertyName("veg")
    boolean VEG;
    @PropertyName("image")
    public String getImage() {
        return Image;
    }
    @PropertyName("image")
    public void setImage(String image) {
        Image = image;
    }
    @PropertyName("image")
    String Image;

    public menuItem()
    {

    }
    public menuItem(String name, String description, String type, int price, String image,boolean veg) {
        Name = name;
        Description = description;
        Type = type;
        Price = price;
        Image=image;
        VEG=veg;


    }
    @PropertyName("name")
    public String getName() {
        return Name;
    }
    @PropertyName("name")
    public void setName(String name) {
        Name = name;
    }
    @PropertyName("description")
    public String getDescription() {
        return Description;
    }
    @PropertyName("description")
    public void setDescription(String description) {
        Description = description;
    }
    @PropertyName("type")
    public String getType() {
        return Type;
    }
    @PropertyName("type")
    public void setType(String type) {
        Type = type;
    }
    @PropertyName("price")
    public int getPrice() {
        return Price;
    }
    @PropertyName("price")
    public void setPrice(int price) {
        Price = price;
    }
    @PropertyName("type")
    String Type;
    @PropertyName("price")
    int Price;

}
