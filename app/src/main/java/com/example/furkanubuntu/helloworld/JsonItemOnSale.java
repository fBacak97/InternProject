package com.example.furkanubuntu.helloworld;

/**
 * Created by furkanubuntu on 6/30/17.
 */

public class JsonItemOnSale {
    String discount;
    String price;
    String description;
    String jsonLink;

    public JsonItemOnSale(String discount, String price, String description, String jsonLink){
        this.description = description;
        this.discount = discount;
        this.price = price;
        this.jsonLink = jsonLink;
    }
}
