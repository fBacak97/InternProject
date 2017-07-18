package com.example.furkanubuntu.helloworld;

import java.io.Serializable;

/**
 * Created by furkanubuntu on 6/30/17.
 */

class JsonItemOnSale implements Serializable {
    String discount;
    String price;
    String description;
    String jsonLink;
    String department;

    JsonItemOnSale(String discount, String price, String description, String jsonLink, String department){
        this.description = description;
        this.discount = discount;
        this.price = price;
        this.jsonLink = jsonLink;
        this.department = department;
    }
}
