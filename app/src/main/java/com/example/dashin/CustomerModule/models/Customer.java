package com.example.dashin.CustomerModule.models;

import com.google.firebase.firestore.PropertyName;

public class Customer {
    private String name;
    private String password;
    private String contact;
    private String email;
    @PropertyName("fcm-token")
    public String getFcm_token() {
        return fcm_token;
    }
    @PropertyName("fcm-token")
    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    @PropertyName("fcm-token")
    private String fcm_token;

    public String getCart_mess_name() {
        return cart_mess_name;
    }

    public void setCart_mess_name(String cart_mess_name) {
        this.cart_mess_name = cart_mess_name;
    }

    private String cart_mess_name;
    public Customer(String name, String password, String contact, String email, String image,String cart_mess_name) {
        this.name = name;
        this.password = password;
        this.contact = contact;
        this.email = email;
        this.image = image;
        this.cart_mess_name=cart_mess_name;
    }

    public Customer(){}

    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
