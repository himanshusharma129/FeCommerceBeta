package com.example.fecommerce;

public class item_recyclerview {
    private String name;
    private String price;
    private String location;

    public item_recyclerview(String name, String price, String location) {
        this.name = name;
        this.price = price;
        this.location = location;
    }

    public item_recyclerview() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
