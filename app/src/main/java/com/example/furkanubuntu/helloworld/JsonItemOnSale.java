package com.example.furkanubuntu.helloworld;

/**
 * Created by furkanubuntu on 6/30/17.
 */

class JsonItemOnSale {
    String discount;
    String price;
    String description;
    String jsonLink;

    JsonItemOnSale(String discount, String price, String description, String jsonLink){
        this.description = description;
        this.discount = discount;
        this.price = price;
        this.jsonLink = jsonLink;
    }
}
