package com.example.furkanubuntu.helloworld;

/**
 * Created by furkanubuntu on 6/21/17.
 */

public class ItemOnSale {
    String discount;
    String price;
    String description;
    int itemPic;

    public ItemOnSale(String discount, String price, String description, int itemPic){
        this.description = description;
        this.discount = discount;
        this.price = price;
        this.itemPic = itemPic;
    }
}
