package com.example.dashin.CustomerModule.models;

import java.util.ArrayList;

public class Address {
    ArrayList<String> location;
    String name;
    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_person() {
        return address_person;
    }

    public void setAddress_person(String address_person) {
        this.address_person = address_person;
    }

    String address_person;

    public Address(ArrayList<String> location, String name, String address, String address_person) {
        this.location = location;
        this.name = name;
        this.address = address;
        this.address_person = address_person;
    }

    public Address(){}
    public ArrayList<String> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<String> location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
