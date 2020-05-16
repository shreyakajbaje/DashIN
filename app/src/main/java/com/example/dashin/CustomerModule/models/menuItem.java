package com.example.dashin.CustomerModule.models;

public class menuItem {
    String Name;
    String Description;

    public boolean isVEG() {
        return VEG;
    }

    public void setVEG(boolean VEG) {
        this.VEG = VEG;
    }

    boolean VEG;
    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    String Type;
    int Price;

}
